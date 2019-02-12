import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os,urllib.request,csv
from pandas.plotting import scatter_matrix

from sklearn import model_selection
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.metrics import accuracy_score

def readInput(file,delim):
    df = pd.read_csv(file,header=None)
    return df

def f(x):
    if x[1] == ' Federal-gov' or x[1] == ' Local-gov' or x[1]==' State-gov': 
        return 'govt'
    elif x[1] == ' Private':
        return 'private'
    elif x[1] == ' Self-emp-inc' or x[1] == ' Self-emp-not-inc': 
        return 'self_employed'
    else: 
        return 'without_pay'


def formatTrainSet(df):
    salary_map={' <=50K':1,' >50K':0}
    df[14]=df[14].map(salary_map).astype(int) #salary <=50K = 1 and >50K = 0
    print (df.head(10))

    df[9] = df[9].map({' Male':1,' Female':0}).astype(int) #male = 1 and female = 0
    print (df.head(10))
    print (("-"*40))
    print (df.info())
    print (df[[13,14]].groupby([13]).mean())

    df.dropna(how='any',inplace=True) 

    data = [df]
    for dataset in data:
        dataset.loc[dataset[13] != ' United-States', 13] = 'Non-US'
        dataset.loc[dataset[13] == ' United-States', 13] = 'US'

    df[13] = df[13].map({'US':1,'Non-US':0}).astype(int)    

    df[[7,14]].groupby(7).mean()
    df[[5,14]].groupby([5]).mean()
    
    df[5] = df[5].replace([' Divorced',' Married-spouse-absent',' Never-married',' Separated',' Widowed'],'Single')
    df[5] = df[5].replace([' Married-AF-spouse',' Married-civ-spouse'],'Couple')

    df[[5,14]].groupby([5]).mean()
    df[[5,7,14]].groupby([5,7]).mean()
    df[[5,7,14]].groupby([7,5]).mean()

    df[5] = df[5].map({'Couple':0,'Single':1})
    rel_map = {' Unmarried':0,' Wife':1,' Husband':2,' Not-in-family':3,' Own-child':4,' Other-relative':5}
    df[7] = df[7].map(rel_map)
    
    df[[8,14]].groupby(8).mean()
    race_map={' White':0,' Amer-Indian-Eskimo':1,' Asian-Pac-Islander':2,' Black':3,' Other':4}
    df[8]= df[8].map(race_map)
    
    df[[6,14]].groupby([6]).mean()
    df[[1,14]].groupby([1]).mean()

    df['employment_type'] = df.apply(f, axis=1)
    df[['employment_type',14]].groupby(['employment_type']).mean()
    employment_map = {'govt':0,'private':1,'self_employed':2,'without_pay':3}
    df['employment_type'] = df['employment_type'].map(employment_map)

    df[[3,14]].groupby([3]).mean()
    df.drop(labels=[1,3,6],axis=1,inplace=True)

    #capital-gain
    df.loc[(df[11] > 0),11] = 1
    df.loc[(df[11] == 0 ,11)]= 0

    #capital-loss
    df.loc[(df[12] > 0),12] = 1 
    df.loc[(df[12] == 0 ,12)]= 0


    print ("\n\n")
    print (df.shape)
    print (df.head(50))
  
def plot_correlation(df, size=15):
    corr= df.corr()
    fig, ax =plt.subplots(figsize=(size,size))
    ax.matshow(corr)
    plt.xticks(range(len(corr.columns)),corr.columns)
    plt.yticks(range(len(corr.columns)),corr.columns)
    plt.show()


def processTrainTestSet(df_train,df_test):
    X = df_train.drop([14],axis=1)
    y = df_train[14]
        
    X_train, X_test, y_train, y_test = train_test_split(X,y,test_size=0.10,random_state=22)
    X_train, X_val, y_train, y_val = train_test_split(X_train,y_train,test_size=0.10,random_state=5)

    print ("\n\nTrain dataset: {0}{1}".format(X_train.shape, y_train.shape))
    print ("Validation dataset: {0}{1}".format(X_val.shape, y_val.shape))
    print ("Test dataset: {0}{1}".format(X_test.shape, y_test.shape))

    return X_train, X_val, y_train, y_val



def testModelsUsingSklearn(X_train, X_val, y_train, y_val):
    models = []
    names = ['LR','Random Forest','Neural Network','GaussianNB','DecisionTreeClassifier','SVM',]

    models.append((LogisticRegression()))
    models.append((RandomForestClassifier(n_estimators=100)))
    models.append((MLPClassifier()))
    models.append((GaussianNB()))
    models.append((DecisionTreeClassifier()))
    models.append((SVC()))

    kfold = model_selection.KFold(n_splits=5, random_state=7)

    for i in range(0,len(models)):    
        cv_result = model_selection.cross_val_score(models[i],X_train,y_train,cv=kfold,scoring='accuracy')
        score=models[i].fit(X_train,y_train) #training
        prediction = models[i].predict(X_val) #validation
        acc_score = accuracy_score(y_val,prediction) #scoring fxn     
        print ('-'*40)
        print ('{0}: {1}'.format(names[i],acc_score))

        

if __name__ == "__main__":
    
    df_train = readInput("input/train.csv",",")
    df_test = readInput("input/test.csv",",")
    
    formatTrainSet(df_train)
    
    #plot_correlation(df_train)
    #printTrainDataInfo(df_train.head(5))
        
    X_train, X_val, y_train, y_val = processTrainTestSet(df_train,df_test)
    testModelsUsingSklearn(X_train, X_val, y_train, y_val)

    
    