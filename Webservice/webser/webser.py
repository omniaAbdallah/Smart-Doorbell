import os
from flask import *
from werkzeug.utils import secure_filename
import json

import doorbell_database

import pymysql
from flask import Response

#import OpenCV module
import cv2
#import os module for reading training data directories and paths
import os
#import numpy to convert python lists to numpy arrays as
#it is needed by OpenCV face recognizers
import numpy as np
from sqlalchemy.sql.functions import random

import doorbell_database ,faceRecognition

app = Flask(__name__,static_url_path='/static')


UPLOAD_FOLDER = './static/uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


doorStatee=[0]

@app.route('/doorstateand/<door>', methods=['GET'])
def doorstateand(door):
    doorStatee.clear()
    if door =="0" or door =="1" :
        doorState=int(door)
        print("door state :",doorState)
        doorStatee.append(doorState)
        x = json.dumps({'door': doorState})
        resp = Response(x, status=200, mimetype='application/json')
        resp.status_code = 200
        return resp


@app.route('/status',methods=['GET'])
def doorrstate ():
    print("len door :",len(doorStatee))
    print("door ",doorStatee)
    if len(doorStatee)==1:
        doorstate=0
        doorStatee.append(doorstate)

    print("door state :",doorStatee[0])
    x = json.dumps({'door': doorStatee[0]})
    resp = Response(x, status=200, mimetype='application/json')
    resp.status_code = 200

    return resp






@app.route('/get_img', methods=['POST'])
def get_img():
    if request.method == 'POST':
        print ("method is post ")
        print("name :" + request.form['submit'])

        file = request.files['fileToUpload']
        print("file if :",file.filename)

    if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            print(filename)
            basedir = os.path.abspath(os.path.dirname(__file__))

            file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
           # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            imgname = "static/uploads/" + filename
            # x = url_for('static', filename=imgname)
            # print("rrrrrrrrrrrrrrrrrrrrrrrrrr"+ x)
            # # imgname="static/uploads/test4.jpg"
            # # imgname="test-data/test(1).jpg"
            print("image name" + imgname)


            trusted_name, trusted_id,confidT = faceRecognition.IS_trusted(imgname)
            block_name, block_id,confidB = faceRecognition.IS_block(imgname)

            print("confidT :",confidT,"\t trusted :",trusted_name)

            print("confidB :", confidB,"\t block :",block_name)

            print(" confidT > confidB",(confidT > confidB))

            print("is trusted : ", trusted_name)
            print("is block : ", block_name)

            from time import gmtime, strftime
            time = strftime("%Y-%m-%d %H:%M", gmtime())

            print("time :", time)
            if (block_name == "unkown" and trusted_name == "unkown"):
                print("insert unkown")
                doorbell_database.insert_history(imgname, "unkown", "nulock", time, "unkown", "unkown")

            elif (block_name != "unkown" and trusted_name == "unkown"):
                print(" insert block ")
                doorbell_database.insert_history(imgname, "block", "nulock", time, block_name, "block")


            elif (block_name == "unkown" and trusted_name != "unkown"):
                print(" insert trusted ")
                relation = doorbell_database.get_truted(int(trusted_id))
                print(" det relation ", trusted_id)
                doorbell_database.insert_history(imgname, "trusted", "lock", time, trusted_name, relation)

            elif block_name != "unkown" and trusted_name != "unkown":
                if(confidB < confidT ):
                    print(" insert block ")
                    doorbell_database.insert_history(imgname, "block", "nulock", time, block_name, "block")
                elif confidB > confidT :
                    print(" insert trusted ")
                    relation = doorbell_database.get_truted(int(trusted_id))
                    print(" det relation ", trusted_id)
                    doorbell_database.insert_history(imgname, "trusted", "lock", time, trusted_name, relation)
                else:
                    print(" can't  classfied ")



            x = json.dumps({'state': imgname})
            resp = Response(x, status=200, mimetype='application/json')
            resp.status_code = 200
            return resp




@app.route('/show_history/<state>', methods=['GET'])
def show_hisory(state):
    if state == "all":
        sql = "SELECT * FROM history"
        print("state :",state)
        print("sql :",sql)
    elif state == "unkown" or  state == "trusted" or state == "block":
        sql = "SELECT * FROM history  WHERE state ='%s'" %(state)
        print("state :", state)
        print("sql :", sql)
    elif state == "lock" or state == "unlock":
        sql = "SELECT * FROM history  WHERE action ='%s'" % (state)
        print("state :", state)
        print("sql :", sql)


    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()
    try:
        a.execute(sql)
        print("excute")
        results = a.fetchall()
        print("print(results)", a)


    except:
        conn.rollback()
        print("rollback")

    conn.close()
    x = json.dumps({'status':'true','history': results})
    resp = Response(x, status=200, mimetype='application/json')
    resp.status_code = 200
    return resp


@app.route('/show_trusted', methods=['GET'])
def show_truset():
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()
    a.execute("SELECT VERSION()")
    data = a.fetchone()
    print("Database version : %s " % data)
    sql = "SELECT * FROM trusted"
    try:
        a.execute(sql)
        print("excute")
        results = a.fetchall()
        print("print(results)", a)


    except:
        conn.rollback()
        print("rollback")

    conn.close()
    x = json.dumps({'trusted': results})
    resp = Response(x, status=200, mimetype='application/json')
    resp.status_code = 200
    return resp


@app.route('/insert_trusted', methods=['POST'])
def insert_truset():
    # name = request.form['name']
    # print('from form name: ' + name)
    if request.method == 'POST':
        print ("method is post ")
        print("name :" + request.form['filename'])
        name =request.form['name']
        print("name :" +name)
        relation=request.form['relation']
        print("relation: "+relation)
        file = request.files['pic']

    if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            print(filename)
            basedir = os.path.abspath(os.path.dirname(__file__))

            file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
           # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            imgname = "static/uploads/" + filename
            print("image name" + imgname)
            state = doorbell_database.insert_truset(name=name, relation=relation, imgname=imgname)

            x = json.dumps({'state': state})
            resp = Response(x, status=200, mimetype='application/json')
            resp.status_code = 200
            return resp

@app.route('/uptate_trusted', methods=['POST'])
def uptate_truset():
        # name = request.form['name']
        # print('from form name: ' + name)
        if request.method == 'POST':
            print("method is post ")
            # print("name :" + request.form['filename'])
            name = request.form['name']
            print("name :" + name)
            id=request.form['id']
            relation = request.form['relation']
            print("relation: " + relation)
            file = request.files['pic']

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            print(filename)
            basedir = os.path.abspath(os.path.dirname(__file__))

            file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
            # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            imgname = "static/uploads/" + filename
            print("image name" + imgname)
            state = doorbell_database.uptate_trusted(name,relation,id,imgname)

        x = json.dumps({'state': state})
        resp = Response(x, status=200, mimetype='application/json')
        resp.status_code = 200
        return resp


@app.route('/delete_trusted', methods=['POST'])
def deletee_trusett():
        # name = request.form['name']
        # print('from form name: ' + name)
        if request.method == 'POST':
            print("method is post ")

            id=request.form['id']
            doorbell_database.delete_trusted(id)
            state='ok'
        x = json.dumps({'state': state})
        resp = Response(x, status=200, mimetype='application/json')
        resp.status_code = 200
        return resp




@app.route('/delete_block', methods=['POST'])
def deletee_block():
        # name = request.form['name']
        # print('from form name: ' + name)
        if request.method == 'POST':
            print("method is post ")

            id=request.form['id']
            doorbell_database.delete_block(id)
            state='ok'
        x = json.dumps({'state': state})
        resp = Response(x, status=200, mimetype='application/json')
        resp.status_code = 200
        return resp
		
@app.route('/show_block', methods=['GET'])
def show_block():
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()

    sql = "SELECT * FROM `block`"
    try:
        a.execute(sql)
        print("excute")
        results = a.fetchall()
        print("print(results)", a)


    except:
        conn.rollback()
        print("rollback")

    conn.close()
    x = json.dumps({'block': results})
    resp = Response(x, status=200, mimetype='application/json')
    resp.status_code = 200
    return resp


@app.route('/insert_block', methods=['POST'])
def insert_block():
    if request.method == 'POST':
        print ("method is post ")
        print("name :" + request.form['filename'])
        name =request.form['name']
        print("name :" +name)

        file = request.files['pic']

    if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            print(filename)
            basedir = os.path.abspath(os.path.dirname(__file__))

            file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
           # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            imgname = "static/uploads/" + filename
            print("image name" + imgname)
            state = doorbell_database.insert_block(name,imgname)

            x = json.dumps({'state': state})
            resp = Response(x, status=200, mimetype='application/json')
            resp.status_code = 200
            return resp

@app.route('/uptate_block', methods=['POST'])
def uptate_block():
        # name = request.form['name']
        # print('from form name: ' + name)
        if request.method == 'POST':
            print("method is post ")
            # print("name :" + request.form['filename'])
            name = request.form['name']
            print("name :" + name)
            id = request.form['id']
            # relation = request.form['relation']
            # print("relation: " + relation)
            file = request.files['pic']

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            print(filename)
            basedir = os.path.abspath(os.path.dirname(__file__))

            file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
            # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            imgname = "static/uploads/" + filename
            print("image name" + imgname)
            state =doorbell_database.uptate_block(name, imgname,id)

        x = json.dumps({'state': state})
        resp = Response(x, status=200, mimetype='application/json')
        resp.status_code = 200
        return resp

		

# @app.route('/uptate_block', methods=['POST'])
# def uptate_block():
#         # name = request.form['name']
#         # print('from form name: ' + name)
#         if request.method == 'POST':
#             print("method is post ")
#             # print("name :" + request.form['filename'])
#             name = request.form['name']
#             print("name :" + name)
#             id=request.form['id']
#
#             file = request.files['pic']
#
#         if file and allowed_file(file.filename):
#             filename = secure_filename(file.filename)
#             print(filename)
#             basedir = os.path.abspath(os.path.dirname(__file__))
#
#             file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
#             # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
#
#             imgname = "static/uploads/" + filename
#             print("image name" + imgname)
#             state = uptate_block(name,imgname,id)
#
#         x = json.dumps({'state': state})
#         resp = Response(x, status=200, mimetype='application/json')
#         resp.status_code = 200
#         return resp
#***********************************************face recognition ***************************************#



def detect_face(img):
    # convert the test image to gray image as opencv face detector expects gray images
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # load OpenCV face detector, I am using LBP which is fast
    # there is also a more accurate but slow Haar classifier
    face_cascade = cv2.CascadeClassifier('opencv-files/lbpcascade_frontalface.xml')

    # let's detect multiscale (some images may be closer to camera than others) images
    # result is a list of faces
    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.2, minNeighbors=5);

    # if no faces are detected then return original img
    if (len(faces) == 0):
        return None, None

    # under the assumption that there will be only one face,
    # extract the face area
    (x, y, w, h) = faces[0]

    # return only the face part of the image
    return gray[y:y + w, x:x + h], faces[0]






def prepare_training_data(images):
    # ------STEP-1--------
    # get the directories (one directory for each subject) in data folder

    # list to hold all subject faces
    faces = []
    # list to hold labels for all subjects
    labels = []

        # label = int(dir_name.replace("s", ""))
    # PATH = os.getcwd()


    for image_name in range(len(images)-1):
        label=image_name
        # print("lable :",label)
        for image_path in images[image_name]:

            image_path="./"+image_path


            print("image path from for 1 :"+image_path)


            # read image
            image = cv2.imread(image_path)

            print("image  from for  :" , image)


            # display an image window to show the image
            cv2.imshow("Training on image...", cv2.resize(image, (400, 500)))
            cv2.waitKey(100)

            # detect face
            face, rect = detect_face(image)

            # ------STEP-4--------
            # for the purpose of this tutorial
            # we will ignore faces that are not detected
            if face is not None:
                # add face to list of faces
                faces.append(face)
                # add label for this face
                labels.append(label)

    cv2.destroyAllWindows()
    cv2.waitKey(1)
    cv2.destroyAllWindows()
    # print("lables :",labels)

    return faces, labels


# First function `draw_rectangle` draws a rectangle on image based on passed rectangle coordinates. It uses OpenCV's built in function `cv2.rectangle(img, topLeftPoint, bottomRightPoint, rgbColor, lineWidth)` to draw rectangle. We will use it to draw a rectangle around the face detected in test image.
#
# Second function `draw_text` uses OpenCV's built in function `cv2.putText(img, text, startPoint, font, fontSize, rgbColor, lineWidth)` to draw text on image.
#
# Now that we have the drawing functions, we just need to call the face recognizer's `predict(face)` method to test our face recognizer on test images. Following function does the prediction for us.

# In[9]:

# this function recognizes the person in image passed
# and draws a rectangle around detected face with name of the
# subject
def predict(test_img , names,face_recognizer):
    # make a copy of the image as we don't want to chang original image
    img = test_img.copy()
    # print("image :",img)
    # detect face from the image
    face, rect = detect_face(img)

    # predict the image using our face recognizer
    label, confidence = face_recognizer.predict(face)
    print("confidence :",confidence ,"name :",names[label])

    if confidence <=50:
        # get name of respective label returned by face recognizer
        label_text = names[label]
    else :
        label_text="unkown"



    # draw a rectangle around face detected
    draw_rectangle(img, rect)
    # draw name of predicted person
    draw_text(img, label_text, rect[0], rect[1] - 5)

    return img,label_text,label


# **Did you notice** that instead of passing `labels` vector directly to face recognizer I am first converting it to **numpy** array? This is because OpenCV expects labels vector to be a `numpy` array.
#
# Still not satisfied? Want to see some action? Next step is the real action, I promise!

# ### Prediction

# Now comes my favorite part, the prediction part. This is where we actually get to see if our algorithm is actually recognizing our trained subjects's faces or not. We will take two test images of our celeberities, detect faces from each of them and then pass those faces to our trained face recognizer to see if it recognizes them.
#
# Below are some utility functions that we will use for drawing bounding box (rectangle) around face and putting celeberity name near the face bounding box.

# In[8]:

# function to draw rectangle on image
# according to given (x, y) coordinates and
# given width and heigh
def draw_rectangle(img, rect):
    (x, y, w, h) = rect
    cv2.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)


# function to draw text on give image starting from
# passed (x, y) coordinates.
def draw_text(img, text, x, y):
    cv2.putText(img, text, (x, y), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 255, 0), 2)


def IS_trusted(image):
    print(image)
    alltrusted, trusted_name,trusted_id = doorbell_database.show_truset()
    print("Preparing data trusted ...")
    faces, labels = prepare_training_data(alltrusted)
    print("Data prepared")

    # print total faces and labels
    print("Total faces: ", len(faces))
    print("Total labels: ", len(labels))

    # create our LBPH face recognizer
    face_recognizer = cv2.face.LBPHFaceRecognizer_create()

    face_recognizer.train(faces, np.array(labels))

    print("Predicting images is trusted or not ?...")

    # load test images
    test_img1 = cv2.imread(image)

    # perform a prediction
    predicted_img1, name,indesx = predict(test_img1,trusted_name,face_recognizer)
    print("Prediction complete")



    return name,trusted_id[indesx]


def IS_block(image):
    allblock, block_name,block_id = doorbell_database.show_block()
    print("Preparing data trusted ...")
    faces, labels = prepare_training_data(allblock)
    print("Data prepared")

    # print total faces and labels
    print("Total faces: ", len(faces))
    print("Total labels: ", len(labels))

    # create our LBPH face recognizer
    face_recognizer = cv2.face.LBPHFaceRecognizer_create()

    face_recognizer.train(faces, np.array(labels))

    print("Predicting images is trusted or not ?...")

    # load test images
    test_img1 = cv2.imread(image)

    # perform a prediction
    predicted_img1, name,index = predict(test_img1,block_name,face_recognizer)
    print("Prediction complete")



    return name,block_id[index]
#

@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5005)

