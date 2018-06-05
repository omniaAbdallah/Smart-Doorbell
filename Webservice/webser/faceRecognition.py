#import OpenCV module
import cv2
#import os module for reading training data directories and paths
import os
#import numpy to convert python lists to numpy arrays as
#it is needed by OpenCV face recognizers
import numpy as np
from sqlalchemy.sql.functions import random

import doorbell_database


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



    for image_name in range(len(images)-1):
        label=image_name
        # print("lable :",label)
        for image_path in images[image_name]:


            # read image
            image = cv2.imread(image_path)

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

    # # display both images
    # cv2.imshow(name, cv2.resize(predicted_img1, (400, 500)))
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()
    # cv2.waitKey(1)
    # cv2.destroyAllWindows()

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

    # # display both images
    # cv2.imshow(name, cv2.resize(predicted_img1, (400, 500)))
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()
    # cv2.waitKey(1)
    # cv2.destroyAllWindows()

    return name,block_id[index]
#
# image="static/uploads/test4.jpg"
# trusted_name,trusted_id=IS_trusted(image)
# block_name,block_id=IS_block(image)
#
#
# print("is trusted : ",trusted_name)
# print("is block : ", block_name)
#
# from time import gmtime, strftime
# time=strftime("%Y-%m-%d %H:%M", gmtime())
# print("time 0",time)
# if block_name=="unkown" and trusted_name=="unkown":
#     imgname = "static/uploads/"
#     print ("insert unkown")
#
# elif block_name!="unkown" and trusted_name=="unkown":
#     imgname = "static/uploads/"
#     print (" insert block ")
#     doorbell_database.insert_history(imgname,"block","nulock",time,block_name,"block")
# elif block_name == "unkown" and trusted_name != "unkown":
#     imgname = "static/uploads/"
#     print(" insert trusted ")
#
#     relation = doorbell_database.get_truted(int(trusted_id))
#     print(" det relation ",trusted_id)
#     doorbell_database.insert_history(image,"trusted","lock",time,trusted_name,relation)
#
# elif block_name!="unkown" and trusted_name!="unkown":
#     imgname = "static/uploads/"
#     print (" can't  classfied ")











# print("Preparing data...")
# faces, labels = prepare_training_data(allblock)
# print("Data prepared")
#
# #print total faces and labels
# print("Total faces: ", len(faces))
# print("Total labels: ", len(labels))
#
# # create our LBPH face recognizer
# face_recognizer = cv2.face.LBPHFaceRecognizer_create()
#
# # or use EigenFaceRecognizer by replacing above line with
# # face_recognizer = cv2.face.EigenFaceRecognizer_create()
#
# # or use FisherFaceRecognizer by replacing above line with
# # face_recognizer = cv2.face.FisherFaceRecognizer_create()
#
#
# # Now that we have initialized our face recognizer and we also have prepared our training data, it's time to train the face recognizer. We will do that by calling the `train(faces-vector, labels-vector)` method of face recognizer.
#
# # In[7]:
#
# # train our face recognizer of our training faces
# face_recognizer.train(faces, np.array(labels))
#
#
# # Now that we have the prediction function well defined, next step is to actually call this function on our test images and display those test images to see if our face recognizer correctly recognized them. So let's do it. This is what we have been waiting for.
#
# # In[10]:
#
# print("Predicting images...")
#
# # load test images
# test_img1 = cv2.imread("test-data/x4.jpg")
#
# test_img2 = cv2.imread("test-data/test(2).jpg")
#
# # perform a prediction
# predicted_img1,name1 = predict(test_img1)
# predicted_img2,name2 = predict(test_img2)
# print("Prediction complete")
#
# # display both images
# cv2.imshow(name1, cv2.resize(predicted_img1, (400, 500)))
# cv2.imshow(name2, cv2.resize(predicted_img2, (400, 500)))
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.waitKey(1)
# cv2.destroyAllWindows()
