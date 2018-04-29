import os
from flask import *
from werkzeug.utils import secure_filename
import json

import pymysql
from flask import Response


app = Flask(__name__)


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

    if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            print(filename)
            basedir = os.path.abspath(os.path.dirname(__file__))

            file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
           # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            imgname = "static/uploads/" + filename
            print("image name" + imgname)

            insert_history(imgname,"unkown","unlock","01:31 5-8-20018","unkown","unkown");


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
            state = insert_truset(name=name, relation=relation, imgname=imgname)

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
            state = uptate_trusted(name,relation,id,imgname)

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
            delete_trusted(id)
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
            delete_block(id)
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
            state = insert_block(name,imgname)

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
            state = uptate_block(name, imgname,id)

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
            id=request.form['id']
           
            file = request.files['pic']

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            print(filename)
            basedir = os.path.abspath(os.path.dirname(__file__))

            file.save(os.path.join(basedir, app.config['UPLOAD_FOLDER'], filename))
            # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            imgname = "static/uploads/" + filename
            print("image name" + imgname)
            state = uptate_block(name,imgname,id)

        x = json.dumps({'state': state})
        resp = Response(x, status=200, mimetype='application/json')
        resp.status_code = 200
        return resp
#******************************* method of databse *******************************#

#******************************* trusted *******************************#


def uptate_trusted(name,relation,id,imagname):
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()
    id=int(id)
    sql = "UPDATE `trusted` SET `name`='%s',`relation`='%s',`imagename`='%s' WHERE `id`='%d' " % \
          (name, relation,imagname,id)
    try:
        a.execute(sql)
        print("excute")
        conn.commit()
        print("commit")
        state = "ok"
    except:
        conn.rollback()
        print("rollback")
        state = "no"

    conn.close()
    return state


def delete_trusted(id):
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()
    id=int(id)
    sql = "DELETE FROM `trusted` WHERE `id`='%d' " % \
          (id)
    try:
        a.execute(sql)
        print("excute")
        conn.commit()
        print("commit")
        state = "ok"
    except:
        conn.rollback()
        print("rollback")
        state = "no"

    conn.close()
    return state


def insert_truset(name,relation,imgname):
   conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                          cursorclass=pymysql.cursors.DictCursor)
   a = conn.cursor()

   sql = "INSERT INTO `trusted`(`name`, `relation`, `imagename`)VALUES ('%s', '%s', '%s')" % \
         (name, relation, imgname)
   try:
      a.execute(sql)
      print("excute")
      conn.commit()
      print("commit")
      state= "ok"
   except:
      conn.rollback()
      print("rollback")
      state="no"

   conn.close()
   return state


   
#******************************* block  *******************************#


def uptate_block(name,imagname,id):
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()
    id=int(id)
    sql = "UPDATE `block` SET `name`='%s',`imagename`='%s' WHERE`id`='%d' " % \
          (name, imagname,id)
    try:
        a.execute(sql)
        print("excute")
        conn.commit()
        print("commit")
        state = "ok"
    except:
        conn.rollback()
        print("rollback")
        state = "no"

    conn.close()
    return state


def delete_block(id):
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()
    id=int(id)
    sql = "DELETE FROM `block` WHERE `id`='%d' " % \
          (id)
    try:
        a.execute(sql)
        print("excute")
        conn.commit()
        print("commit")
        state = "ok"
    except:
        conn.rollback()
        print("rollback")
        state = "no"

    conn.close()
    return state


def insert_block (name,imgname):
   conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                          cursorclass=pymysql.cursors.DictCursor)
   a = conn.cursor()

   sql = "INSERT INTO `block`( `name`, `imagename`) VALUES ('%s','%s')" % \
         (name, imgname)
   try:
      a.execute(sql)
      print("excute")
      conn.commit()
      print("commit")
      state= "ok"
   except:
      conn.rollback()
      print("rollback")
      state="no"

   conn.close()
   return state
#*****************************************history****************************#
def insert_history( imgname, state, action, time, name, relation ):
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()

    sql2 = "INSERT INTO `history`( `imagename`, `state`, `action`, `time`, `name`, `relation`) VALUES ('%s','%s','%s','%s','%s','%s')" % \
           (imgname, state, action, time, name, relation)
    try:
        a.execute(sql2)
        print("excute")
        conn.commit()
        print("commit")
        state = "ok"
    except:
        conn.rollback()
        print("rollback")
        state = "no"

    conn.close()
    return state

@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5005)

