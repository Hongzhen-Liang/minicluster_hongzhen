#!/usr/bin/python3

from xml.dom.minidom import parse
import xml.dom.minidom
import sys
import os


def file_name_xml(file_dir):
    xmls_name=[]
    for root, dirs, files in os.walk(file_dir):
        for file in files:
            if file[-4:] == ".xml":
                xmls_name.append(file)
    return xmls_name

def print_failure(Property,NewValue,DTest,file_dir,xmls_name):
    if not os.path.exists("analyze/"+DTest):
        os.makedirs("analyze/"+DTest)
    f_scrach_dir="analyze/%s/scrach_error_%s_%s_%s.txt"%(DTest,Property,NewValue,DTest)
    f_detail_dir="analyze/%s/detail_error_%s_%s_%s.txt"%(DTest,Property,NewValue,DTest)
    f_scrach = open(f_scrach_dir,"w")
    f_detail = open(f_detail_dir,"w")
    
    
    for xml_name in xmls_name:
        DOMTree = xml.dom.minidom.parse(file_dir+xml_name)
        collection = DOMTree.documentElement
        if len(collection.getElementsByTagName("failure"))==0:
            continue
        f_scrach.write(collection.getAttribute("name")+"\n")
        Testcases = collection.getElementsByTagName("testcase")
        for Testcase in Testcases:
            Errors=Testcase.getElementsByTagName("failure")
            if len(Errors) != 0:
                print(Testcase.getAttribute("name"))
                #f_scrach.write(Testcase.getAttribute("name")+"\n")
                for Error in Errors:
                    # print(Error.toprettyxml(indent=' '))
                    f_detail.write(Testcase.getAttribute("name")+"\n")
                    f_detail.write(Error.toprettyxml(indent=' ')+"\n")
            else:
                print("zero error")
    f_scrach.close()
    f_detail.close()
    
if __name__ == "__main__":
    file_dir = "target/surefire-reports/"
    xmls_name = file_name_xml(file_dir)
    print_failure(sys.argv[1],sys.argv[2],sys.argv[3],file_dir,xmls_name)
    #print_failure(sys.argv[3],sys.argv[1]+"_"+sys.argv[2],sys.argv[1]+"_"+sys.argv[2],file_dir,xmls_name)
    




