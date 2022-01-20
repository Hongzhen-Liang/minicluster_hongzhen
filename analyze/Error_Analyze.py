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

def print_failure(scrach_file_dir,detail_file_dir,file_dir,xmls_name):
    f_scrach = open(scrach_file_dir,"w")
    f_detail = open(detail_file_dir,"w")
    for xml_name in xmls_name:
        DOMTree = xml.dom.minidom.parse(file_dir+xml_name)
        collection = DOMTree.documentElement
        Testcases = collection.getElementsByTagName("testcase")
        for Testcase in Testcases:
            Errors=Testcase.getElementsByTagName("failure")
            if len(Errors) != 0:
                print(Testcase.getAttribute("name"))
                f_scrach.write(Testcase.getAttribute("name")+"\n")
                for Error in Errors:
                    # print(Error.toprettyxml(indent=' '))
                    f_detail.write(Error.toprettyxml(indent=' ')+"\n")
            else:
                print("zero error")
    f_scrach.close()
    f_detail.close()
if __name__ == "__main__":
    file_dir = "../target/surefire-reports/"
    xmls_name = file_name_xml(file_dir)
    print_failure("scrach_error.txt","detail_error.txt",file_dir,xmls_name)




