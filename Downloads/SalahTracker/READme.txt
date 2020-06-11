1) FYP_Classifier:
It contains the Machine learning code which trains the model on data and saves the model as a PKL file.
FullData.csv is the file which contains avgs of new Data. Each row in the file denotes avg of 4 rows. 

2) FYP_Final:
It contains the Final Android App
For Running app, Android Studio version 4.0 is required and server Ip should be entered
in code in Activity StartNamaz(Line 376). 

3) FYPServer:
Python File which runs as a server which loads the saved model (finalized_model.sav ) and (Scaler.pkl)
and predicts the Label on real time.
Server and Client(App) are connected via socket Programming.

4) FYP_1:
This App is used to collect android accelerometer data while the user is performing namaz.
This app was used to collect training data.

5) FYP_Tagging:
This App was also used while data collection to label the training data.

6) DATA:
This folder all the data original and merged files.