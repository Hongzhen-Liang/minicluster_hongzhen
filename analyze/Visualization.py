import os
import re
from matplotlib import pyplot as plt 

def file_names(file_dir,pattern):
    file_names=[]
    for root, dirs, files in os.walk(file_dir):
        for file in files:
            if re.search(pattern,file):
                file_names.append(file)
    return file_names

def Visualize(file_dir,file_names):
    Xs=[]
    Ys=[]
    for file in file_names:
        Xs.append(file)
        Ys.append(len(open(file_dir+file,"rU").readlines()))
    plt.title("Online Configure Error analyze")
    plt.xlabel("Parameter") 
    plt.ylabel("Error numbers") 
    
    for x,y in zip(Xs,Ys):
        plt.text(x, y+0.05, '%.0f' % y, ha='center', va= 'bottom',fontsize=11)
    plt.bar(Xs,Ys)
    plt.savefig('Online Configure Error analyze.jpg')    
    #plt.show()
        
    
file_dir = "Finish/"
pattern = "scrach"
file_names=file_names(file_dir,pattern)
Visualize(file_dir,file_names)