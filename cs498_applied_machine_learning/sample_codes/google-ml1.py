
# below is a sample from Google videos: https://youtu.be/cKxRvEZd3Mw?list=PLOU2XLYxmsIIuiBfYad6rFYQU_jL2ryal
from sklearn import tree

#below features are for apples and oranges

# --> 1) step 1 is to collect the training data.
#features = [[140,"smooth"],[130,"smooth"],[150,"bumpy"],[150,"bumpy"]]
#labels = ["apple","apple","orange","orange"]

# note the above strings are converted to integers because scikit-learn uses real-valued features
features = [[140,1],[130,1],[150,0],[150,0]] 
labels = [0,0,1,1]

# --> 2) Train the classifier. Type of classifier : Decision Tree
clf = tree.DecisionTreeClassifier() # empty box of rules and doesnt know about the apples and oranges.

# --> 3) create a learning algorithm to train the clf. learning algorithm is a procedure to create these rules for apples and oranges.
clf = clf.fit(features, labels) #--> this is a trained classifier.

print (clf.predict([[150,0]]))


