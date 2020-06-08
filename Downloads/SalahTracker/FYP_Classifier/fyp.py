import pandas as pd
import numpy as np
import pickle

from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import classification_report, confusion_matrix


#df = pd.read_csv('NewTrain.csv')
df = pd.read_csv('data7k.csv')
# df2 = pd.read_csv('Data2.csv')
# df3 = pd.read_csv('Data3.csv')
# df4 = pd.read_csv('Data4.csv')
# df5 = pd.read_csv('Data5.csv')


df.head()
#df=df.append(df2,ignore_index=True, sort=False)
#df=df.append(df3,ignore_index=True, sort=False)
#df=df.append(df4,ignore_index=True, sort=False)
#df=df.append(df5,ignore_index=True, sort=False)

#print(df)
print(len(df))
narr= df.to_numpy()


#print(df['Label'])
labels = np.unique(df['Label'])
print(labels)

index=[0,1,2,4]

narr2= (df[df['Label'] == labels[0]]).to_numpy()[:,index]

while (len(narr2) < 200):
    narr2 = np.append(narr2, narr2,axis=0)
np.random.shuffle(narr2)


#narrF = np.empty(shape=(8*200, 4))
narrF=narr2[0:200]

for i in range(1,len(labels)):
    narr2= (df[df['Label'] == labels[i]]).to_numpy()[:,index]

    while (len(narr2) < 200):
        narr2 = np.append(narr2, narr2,axis=0)
    np.random.shuffle(narr2)
    narrF=np.append(narrF,narr2[0:200],axis=0)


print( len(narr2))

#print(narrF)
#np.append(narr2[:200])

#print(narr2)
#print (len(df[df['Label'] == labels[4]]))

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')


colors = "bgrcmykw"
for i in range(len(labels)):
    tempdf = df[df['Label'] == labels[i]]

    angleX = tempdf['angleX']
    angleY = tempdf['angleY']
    angleZ = tempdf['angleZ']


    ax.scatter(angleX, angleY, angleZ,c=colors[i] ,marker='o')
    ax.set_xlabel('X Label')
    ax.set_ylabel('Y Label')
    ax.set_zlabel('Z Label')

plt.show()

np.random.shuffle(narrF)

# Classifier Model     KNN

X = narr[:, 0:3]
y = narr[:, 4]
#print(X,y)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
#print(X,y)



scaler = StandardScaler()
scaler.fit(X_train)

X_train = scaler.transform(X_train)
X_test = scaler.transform(X_test)



classifier = KNeighborsClassifier(n_neighbors=5)
classifier.fit(X_train, y_train)
y_pred = classifier.predict(X_test)



print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))


# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ save the model to disk
#filename = 'finalized_model.sav'
#pickle.dump(classifier, open(filename, 'wb'))
#pickle.dump(scaler, open('scaler.pkl', 'wb'))
# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



from sklearn.metrics import accuracy_score

print ("KNN Accuracy: ", accuracy_score(y_test, y_pred))

from sklearn import tree

model = tree.DecisionTreeClassifier()

model.fit(X_train, y_train)

y_predict = model.predict(X_test)

from sklearn.metrics import accuracy_score

print ("Decision Trees Accuracy: ", accuracy_score(y_test, y_predict))
