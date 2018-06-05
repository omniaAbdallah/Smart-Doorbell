import pymysql

def insert_truset (name, relation, imgname):
   # Open database connection
   conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                          cursorclass=pymysql.cursors.DictCursor)
   # prepare a cursor object using cursor() method
   a = conn.cursor()
   # execute SQL query using execute() method.
   a.execute("SELECT VERSION()")
   # Fetch a single row using fetchone() method.
   data = a.fetchone()
   print("Database version : %s " % data)

   # Prepare SQL query to INSERT a record into the database.
   sql = "INSERT INTO `trusted`(`name`, `relation`, `imagename`)VALUES ('%s', '%s', '%s')" % \
         (name, relation, imgname)
   try:
      # Execute the SQL command
      a.execute(sql)
      print("excute")
      # Commit your changes in the database
      conn.commit()
      print("commit")
   except:
      # Rollback in case there is any error
      conn.rollback()
      print("rollback")

   # disconnect from server
   conn.close()


def show_block():
   # Open database connection
   conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                          cursorclass=pymysql.cursors.DictCursor)
   # prepare a cursor object using cursor() method
   a = conn.cursor()
   # execute SQL query using execute() method.
   a.execute("SELECT VERSION()")
   # Fetch a single row using fetchone() method.
   data = a.fetchone()
   # print("Database version : %s " % data)
   # Prepare SQL query to INSERT a record into the database.
   sql = "SELECT name, imagename,image1,image2,image3,image4,image5 FROM block"

   allblock=[]
   block_name=[]

   try:
      # Execute the SQL command
      a.execute(sql)
      # print("excute")
      results = a.fetchall()
      # print(" trusted",results)
      for row in results:
         # print("in for")
         block=[]
         block_name.append(row["name"])
         block.append( row["imagename"])
         block.append(row["image1"])
         block.append(row["image2"])
         block.append(row["image3"])
         block.append(row["image4"])
         block.append(row["image5"])


         # print("block in for :",block)
         allblock.append(block)
         # print("all block in for :",allblock)
      #
      # print("allblock ",allblock)
      # print(" name of block :",block_name)
         #    print("imagename :" + imagename)
      #    name = row["name"]
      #    print("name :" + name)
      #    relation = row["relation"]
      #    print("relation :" + relation)
      #
   except:
      # Rollback in case there is any error
      conn.rollback()
      print("rollback")

   # disconnect from server
   conn.close()
   return allblock,block_name


def show_truset():
   # Open database connection
   conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                          cursorclass=pymysql.cursors.DictCursor)
   # prepare a cursor object using cursor() method
   a = conn.cursor()
   # execute SQL query using execute() method.
   a.execute("SELECT VERSION()")
   # Fetch a single row using fetchone() method.
   data = a.fetchone()
   # print("Database version : %s " % data)
   # Prepare SQL query to INSERT a record into the database.
   sql = "SELECT name, imagename,image1,image2,image3,image4,image5 FROM trusted"

   allblock=[]
   block_name=[]

   try:
      # Execute the SQL command
      a.execute(sql)
      # print("excute")
      results = a.fetchall()
      # print(" trusted",results)
      for row in results:
         # print("in for")
         block=[]
         block_name.append(row["name"])
         block.append( row["imagename"])
         block.append(row["image1"])
         block.append(row["image2"])
         block.append(row["image3"])
         block.append(row["image4"])
         block.append(row["image5"])


         # print("block in for :",block)
         allblock.append(block)
         # print("all block in for :",allblock)

      # print("allblock ",allblock)
      # print(" name of block :",block_name)
      #    #    print("imagename :" + imagename)
      # #    name = row["name"]
      # #    print("name :" + name)
      # #    relation = row["relation"]
      # #    print("relation :" + relation)
      # #
   except:
      # Rollback in case there is any error
      conn.rollback()
      print("rollback")

   # disconnect from server
   conn.close()
   return allblock,block_name


def uptate_trusted(name,relation,imagname,id):
    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
                           cursorclass=pymysql.cursors.DictCursor)
    a = conn.cursor()
    id=int(id)
    sql = "UPDATE `trusted` SET `name`='%s',`relation`='%s',`imagename`='%s' WHERE `id`='%d' " % \
          (name, relation, imagname,id)
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

# def insert_block (name,imgname):
#    conn = pymysql.connect(host='localhost', user='root', password='', db='doorbell', charset='utf8mb4',
#                           cursorclass=pymysql.cursors.DictCursor)
#    a = conn.cursor()
#
#    sql = "INSERT INTO `block`( `name`, `imagename`) VALUES ('%s','%s')" % \
#          (name, imgname)
#
#    sql2="INSERT INTO `history`( `imagename`, `state`, `action`, `time`, `name`, `relation`) VALUES ('%s','%s','%s','%s','%s','%s')" %\
#         (imgname,state,action,time,name,relation)
#    try:
#       a.execute(sql)
#       print("excute")
#       conn.commit()
#       print("commit from block")
#       state= "ok"
#    except:
#       conn.rollback()
#       print("rollback")
#       state="no"
#
#    conn.close()

show_truset()
# delete_trusted(22)
# delete_trusted(23)
# delete_trusted(24)
# delete_trusted(25)
#
#
# insert_block('bbb',r"static/uploads/drbost.11.jpg")
# insert_block('ccc',r"static/uploads/astefa.jpg")
# insert_block('ddd',r"static/uploads/elduns.11.jpg")
# insert_block('eee',r"static/uploads/asewil.jpg")
#
#
#DELETE FROM `trusted` WHERE 1
#insert_truset("aballah","father","Abdallah")
#show_truset()
#print(delete_trusted("4"))

