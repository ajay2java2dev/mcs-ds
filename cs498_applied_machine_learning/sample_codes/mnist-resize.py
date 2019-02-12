#I am using this class to load the MNIST dataset.

import os,urllib.request
import gzip,shutil,codecs,numpy
import cv2

from skimage.io import imsave

datapath = '../Data/MNISTData/'

if not os.path.exists(datapath):
    os.makedirs(datapath)

urls = ['http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz',
       'http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz',
       'http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz',
       'http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz']

for url in urls:
    filename = url.split('/')[-1]   # GET FILENAME
    
    if os.path.exists(datapath+filename):
        print(filename, ' already exists')  # CHECK IF FILE EXISTS
    else:
        print('Downloading ',filename)
        urllib.request.urlretrieve (url, datapath+filename) # DOWNLOAD FILE
     
print('All files are available')

files = os.listdir(datapath)
for file in files:
    if file.endswith('gz'):
        print('Extracting ',file)
        with gzip.open(datapath+file, 'rb') as f_in:
            with open(datapath+file.split('.')[0], 'wb') as f_out:
                shutil.copyfileobj(f_in, f_out)
print('Extraction Complete')

# OPTIONAL REMOVE THE ARCHIVES
for file in files:
    print('Removing ',file)
    os.remove(datapath+file)
print ('All archives removed')

files = os.listdir(datapath)

def get_int(b):   # CONVERTS 4 BYTES TO A INT
    return int(codecs.encode(b, 'hex'), 16)

data_dict = {}
for file in files:
    if file.endswith('ubyte'):  # FOR ALL 'ubyte' FILES
        print('Reading ',file)
        with open (datapath+file,'rb') as f:
            data = f.read()
            type = get_int(data[:4])   # 0-3: THE MAGIC NUMBER TO WHETHER IMAGE OR LABEL
            length = get_int(data[4:8])  # 4-7: LENGTH OF THE ARRAY  (DIMENSION 0)
            
            if (type == 2051):
                category = 'images'
                num_rows = get_int(data[8:12])  # NUMBER OF ROWS  (DIMENSION 1)
                num_cols = get_int(data[12:16])  # NUMBER OF COLUMNS  (DIMENSION 2)
                parsed = numpy.frombuffer(data,dtype = numpy.uint8, offset = 16)  # READ THE PIXEL VALUES AS INTEGERS
                parsed = parsed.reshape(length,num_rows,num_cols)  # RESHAPE THE ARRAY AS [NO_OF_SAMPLES x HEIGHT x WIDTH]           
            elif(type == 2049):
                category = 'labels'
                parsed = numpy.frombuffer(data, dtype=numpy.uint8, offset=8) # READ THE LABEL VALUES AS INTEGERS
                parsed = parsed.reshape(length)  # RESHAPE THE ARRAY AS [NO_OF_SAMPLES]                           
            
            if (length==10000):
                set = 'test'
            elif (length==60000):
                set = 'train'
            data_dict[set+'_'+category] = parsed  # SAVE THE NUMPY ARRAY TO A CORRESPONDING KEY     

sets = ['train','test']


for set in sets:   # FOR TRAIN AND TEST SET
    images = data_dict[set+'_images']   # IMAGES
    labels = data_dict[set+'_labels']   # LABELS
    no_of_samples = images.shape[0]     # NUBMER OF SAMPLES
    for indx in range (no_of_samples):  # FOR EVERY SAMPLE
        print(set, indx)
        image = images[indx]            # GET IMAGE
        label = labels[indx]            # GET LABEL
        if not os.path.exists(datapath+set+'/'+str(label)+'/'):    # IF DIRECTORIES DO NOT EXIST THEN 
            os.makedirs (datapath+set+'/'+str(label)+'/')       # CREATE TRAIN/TEST DIRECTORY AND CLASS SPECIFIC SUBDIRECTORY
        filenumber = len(os.listdir(datapath+set+'/'+str(label)+'/'))  # NUMBER OF FILES IN THE DIRECTORY FOR NAMING THE FILE
        imsave(datapath+set+'/'+str(label)+'/%05d.png'%(filenumber),image)  # SAVE THE IMAGE WITH PROPER NAME
