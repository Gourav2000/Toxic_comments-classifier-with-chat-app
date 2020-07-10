# Censored_messenger_application
Identifies toxic comments and censors it
<h2>Getting started</h2>
As it uses firebase as its back-end, to get it working for you simply need to create a account in firebase console by linking it with your gogle account, you are good to go if u have one already.Create a project and link your android app with
firebase by copying your google-services.json file from firebse and place it in the app directory of the project.
<h2>Working</h2>
It basically works with the help of ml model created with the help of @praj000 using nlp. Evrey message u pass is verified by the mnodel and censored if found toxic.
first of all 

[download](https://1drv.ms/u/s!AkKbxoO0cGGxizGcXph9qR6xLaWG)

the model from here place it in the same directory as Ok.py and tokenizer.pickle, now in the firebase project yopu created add an app and select the web platform, now from the firebase sdk copy the values from firebase config, now edit the Ok.py file and place the copied values from the firebase config to the config part at the Ok.py file
 Now run the Ok.py file without it the app won't work...and you are good to go ;-)
 
 [download](https://1drv.ms/u/s!AkKbxoO0cGGxizGcXph9qR6xLaWG)
