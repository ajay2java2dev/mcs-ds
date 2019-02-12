import numpy as np
import matplotlib.pyplot as plt

#approximating a functions

#input data - Of the form [X value, Y value, Bias term]
X = np.array([[-2, 4, -1],[4, 1, -1],[1, 6, -1],[2, 4, -1],[6, 2, -1],])

#Associated output labels - First 2 examples are labeled '-1' and last 3 are labeled 1
Y = np.array([-1,-1,1,1,1])

for d, sample in enumerate(X):
    #Plot the negative samples (the first 2)
    if d < 2:
        plt.scatter(sample[0], sample[1], s = 120, marker="_", linewidths=2)
    #Plot the positive samples (the last 3)
    else:
        plt.scatter(sample[0], sample[1], s = 120, marker="+",linewidths=2)

plt.plot([-2,6],[6,0.5])
#plt.show()

#lets perform stochastic gradient descent to learn tthe seperating hyperplane
def svm_sgd_plot (X, Y):
    #Initialize our svms weight vector with zeros (3 values)
    w = np.zeros(len(X[0]))
    #The learning rate
    eta = 1
    #how many iterations to train for
    epochs = 100000
    #store misclassifications so we can plot how they change over time
    errors = []
    
    for epoch in range (1,epochs):
        error = 0
        for i, x in enumerate(X):
            if (Y[i]*np.dot(X[i], w)) < 1:
                #misclassified update for ours weights
                w = w + eta * ( (X[i] * Y[i]) + (-2 * (1 / epoch) * w))
                error = 1
            else:
                #correct classification, update our weights. 1/epoch -> regularization term
                w = w + eta  * (-2 * (1/epoch) * w)
        errors.append(error)
        
    plt.plot(errors,'|')
    plt.ylim(0.5,1.5)
    plt.axes().set_yticklabels([])
    plt.xlabel('Epoch')
    plt.ylabel('Misclassified')
    plt.show
    
    return w

w = svm_sgd_plot(X,Y)

for d, sample in enumerate(X):
    #Plot the negative samples (the first 2)
    if d < 2:
        plt.scatter(sample[0], sample[1], s = 120, marker="_", linewidths=2)
    #Plot the positive samples (the last 3)
    else:
        plt.scatter(sample[0], sample[1], s = 120, marker="+",linewidths=2)

# Add our test samples
plt.scatter(2,2, s=120, marker='_', linewidths=2, color='yellow')
plt.scatter(4,3, s=120, marker='+', linewidths=2, color='blue')

#print the hyperplane calcuated by svm_sgc()
x2=[w[0],w[1],-w[1],w[0]]
x3=[w[0],w[1],w[1],-w[0]]

x2x3 = np.array([x2,x3])
X,Y,U,V = zip(*x2x3)
ax = plt.gca()
ax.quiver(X,Y,U,V,scale=1, color='blue')