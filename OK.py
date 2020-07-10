# -*- coding: utf-8 -*-
"""
Created on Wed May  6 07:01:42 2020

@author: Prj
"""

import pyrebase
import tensorflow.keras as tf


import pickle
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
config={
   "apiKey": "",
    "authDomain": "",
    "databaseURL": "",
    "projectId": "",
    "storageBucket": "",
    "messagingSenderId": "",
    "appId": "",
    "measurementId": ""
}
model=tf.models.load_model("Toxicity.h5")
with open("tokenizer.pickle","rb") as file:
    l=pickle.load(file)

firebase=pyrebase.initialize_app(config)
db=firebase.database()
data=db.get().val()
size=0
message=""
corrected=""
c=0
while (1):
    data=db.get().val()
    if data==None:
        continue
    if len(data)>size:
        for i in data:
            if data[i]["corrected"]=="/empty/":
                message=data[i]["message"]
                
                g=l.texts_to_sequences([message])
                g=pad_sequences(g,maxlen=120,padding="post",truncating="post")
                pred=model.predict(g)
                f=0
                Identity=0
                
                f=1 if pred[0][0]>0.5 else 0
                Identity=1 if pred[0][-1]>0.35 else 0
                print(message)
                if(f and not Identity):
                    message="***"
                    print("----> its toxic")
                elif(f and Identity):
                    message="*********************"
                else:
                    pass
                #try some longer sentences
                # try longer sentences 
                # try nigga stuffs 
                #lol
                
                    
                    
                corrected=message#something round here may be
                db.child(i).update({"corrected":corrected})
                #print
                
                
        
    size=len(data)