import json
import requests
import random
from datetime import date
from datetime import datetime
from operator import itemgetter
from math import sin, cos, sqrt, atan2, radians

import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

from django.http import *
from django.db.models import Q
from django.shortcuts import render, redirect
from django.core.exceptions import ObjectDoesNotExist

from .forms import *
from .models import *
from .mail_function import *

##

# CONSTANTS

MIN_DISTANCE = 500
MAIL_ON = False

##

##

#WEB PART

##


#DASHBOARD DATA TO DISPLAY
def getDashData(usr):
    comps =  list(RoadComplaintToOfficer.objects.filter(officer_id=usr))
    total_req = len(comps)
    EmergencyRequests = []
    IncompleteRequests = []
    completedRequests = []
    for cm in comps:
        if int(cm.complaint_id.isEmergency) == 1 and str(cm.complaint_id.workStatus) != 'complete':
            EmergencyRequests.append(cm.complaint_id)
    for cm in comps:
        if str(cm.complaint_id.workStatus) != 'complete':
            IncompleteRequests.append(cm.complaint_id)
        if str(cm.complaint_id.workStatus) == "complete":
            completedRequests.append(cm.complaint_id)

    data = {
        'total_req': total_req,
        'Emergency': EmergencyRequests,
        'emgCnt': len(EmergencyRequests),
        'Incomplete': len(IncompleteRequests),
        'completed': len(completedRequests)
    }
    return data

#HOME PAGE IN WEB PORTAL
def Index(request):
    #ON LOGIN....
    if request.method == "POST":
        loginForm = LoginForm(request.POST)
        if loginForm.is_valid():
            email = loginForm.cleaned_data['email']
            password = loginForm.cleaned_data['password']

            try:
                tempUsr = Users.objects.get(email=email)
            except ObjectDoesNotExist:
                return HttpResponse("User not found")

            #NO GENERAL PEOPLE CAN LOGIN
            if tempUsr.user_type == "user":
                return HttpResponse("Normal users have to login with app")

            if tempUsr.password == password:
                con = {
                    'user_object': tempUsr,
                    'id': int(tempUsr.id),
                    'name': str(tempUsr.name),
                    'user_type': str(tempUsr.user_type),
                    'data': getDashData(usr=tempUsr)
                }
                if tempUsr.user_type == "admin":

                    totalUsers = len(Users.objects.filter(user_type="user"))
                    totalComplaints = len(RoadComplaint.objects.all())
                    HierarchyLevel = len(RoleTable.objects.all())
                    TotalOfficers = len(Users.objects.exclude(user_type="user"))
                    #
                    # if 'ach' in request.GET and request.GET['arc'] == 0:
                    #     pass

                    con = {
                        'user_object': tempUsr,
                        'id': int(tempUsr.id),
                        'name': str(tempUsr.name),
                        'user_type': str(tempUsr.user_type),
                        'totalUsers': totalUsers,
                        'totalComplaints': totalComplaints,
                        'HierarchyLevel': HierarchyLevel,
                        'TotalOfficers': TotalOfficers
                    }
                    #SET ADMIN DETAILS IN COOKIES
                    response = render(request, 'roadGriev/adminIndex.html', con)
                    response.set_cookie('roadGrievAdmin', str(tempUsr.id), max_age=36000)
                    return response
                #SET OFFICER'S DETAILS IN COOKIES
                response = render(request, 'roadGriev/index.html', con)
                response.set_cookie('roadGrievUser', str(tempUsr.id), max_age=36000)
                return response
            #PASSWORD ERROR
            return HttpResponse("Password not matched")
        #INVALID LOGIN DETAILS
        return HttpResponse("Form not valid")

    # NORMAL LOGGED IN OFFICER
    if 'roadGrievUser' in request.COOKIES:
        usrId = request.COOKIES['roadGrievUser']
        usr = Users.objects.get(id=usrId)
        #complaints = list(RoadComplaint.objects.filter(current_officer=usr))

        con = {
            'user_object': usr,
            'id': int(usr.id),
            'name': str(usr.name),
            'user_type': str(usr.user_type),
            'data': getDashData(usr)
        }
        return render(request, 'roadGriev/index.html', con)
    #LOGGED IN ADMIN
    elif 'roadGrievAdmin' in request.COOKIES:
        usrId = request.COOKIES['roadGrievAdmin']
        usr = Users.objects.get(id=usrId)

        totalUsers = len(Users.objects.filter(user_type="user"))
        totalComplaints = len(RoadComplaint.objects.all())
        HierarchyLevel = len(RoleTable.objects.all())
        TotalOfficers = len(Users.objects.exclude( Q(user_type="user") | Q(user_type="admin") ))

        migratedc = ArchivedRoadComplaint.objects.all()

        con = {
            'user_object': usr,
            'id': int(usr.id),
            'name': str(usr.name),
            'user_type': str(usr.user_type),
            'totalUsers': totalUsers,
            'totalComplaints': totalComplaints,
            'HierarchyLevel': HierarchyLevel,
            'TotalOfficers': TotalOfficers,
            'Migrated': migratedc
        }
        return render(request, 'roadGriev/adminIndex.html', con)

    #DEFAULT LOGIN PAGE....
    return render(request, 'roadGriev/login.html')


#TOTAL REQUESTS OF CURRENT OFFICER
def incRequests(request):
    if 'roadGrievUser' in request.COOKIES:
        usrId = request.COOKIES['roadGrievUser']
        usr = Users.objects.get(id=usrId)
        comps = RoadComplaintToOfficer.objects.filter(officer_id=usr)
        con = {
            'id': int(usr.id),
            'name': str(usr.name),
            'user_type': str(usr.user_type),
            'requests': comps
        }
        return render(request, 'roadGriev/requests.html', con)
    return HttpResponse("Permission denied")

#REPORTS OF REQUESTS OF CURRENT OFFICER
def incReports(request):
    if 'roadGrievUser' in request.COOKIES:
        usrId = request.COOKIES['roadGrievUser']
        usr = Users.objects.get(id=usrId)
        con = {
            'id': int(usr.id),
            'name': str(usr.name),
            'user_type': str(usr.user_type)
        }
        return render(request, 'roadGriev/reports.html', con)
    return HttpResponse("Permission denied")


#DISPLAY ONE COMPLAINT FULL
def complaintFull(request, complaint_id):
    if 'roadGrievUser' in request.COOKIES:

        try:
            usrId = request.COOKIES['roadGrievUser']
            usr = Users.objects.get(id=usrId)
            compl = RoadComplaint.objects.get(id=complaint_id)
        except ObjectDoesNotExist:
            return HttpResponse("complaint not found")

        PRE_FLAG = 0
        if int(usrId) == int(compl.current_officer.id):
            PRE_FLAG = 1
        sts = 0
        if str(compl.workStatus) == "pending":
            sts = 1
        elif str(compl.workStatus) == "validated":
            sts = 2
        elif str(compl.workStatus) == "in progress":
            sts = 3
        elif str(compl.workStatus) == "complete":
            sts = 4
        elif str(compl.workStatus) == "disapproved":
            sts = 5
        try:
            usrs = RoadComplaintPostedUser.objects.filter(complaint_id=compl)
        except ObjectDoesNotExist:
            return HttpResponse("Error")

        roles = RoleTable.objects.all()

        setEtime = 1
        if compl.esitmated_time == None:
            setEtime = 0

        offrole = RoleTable.objects.get(role_name=str(usr.user_type))

        con = {
            'sts': sts,
            'Officer': usr,
            'PRE_FLAG': PRE_FLAG,
            'complaint': compl,
            'users': list(usrs),
            'roles': list(roles),
            'setEtime': setEtime,
            'Officer_weight': offrole.weight,
            'current_date': date.today()
        }
        return render(request, 'roadGriev/complaintFull.html', con)
    return HttpResponse("Permission denied")

#SETTIG UP ESTIMATED TIME FOR COMPLETION OF GIVEN COMPLAINT
def setEstimatedTime(request):
    if request.method == "GET" and 'complaint_id' in request.GET and 'officer_id' in request.GET:
        complaint_id = request.GET['complaint_id']
        if 'time' in request.GET:
            time = request.GET['time']
            cmpl = RoadComplaint.objects.get(id=complaint_id)
            cmpl.esitmated_time = time
            cmpl.save()
            data = {
                'status': 0,
                'data': {
                    'message': "Done"
                }
            }
            return JsonResponse(data)
        if 'setNullTime' in request.GET and request.GET['setNullTime'] == 1:
            cmpl = RoadComplaint.objects.get(id=complaint_id)
            cmpl.esitmated_time = None
            cmpl.save()
            data = {
                'status': 0,
                'data': {
                    'message': "Done"
                }
            }
            return JsonResponse(data)
    data = {
        'status': 1,
        'error': {
            'message': "Access denied"
        }
    }
    return JsonResponse(data)

##

# ADMIN PANEL

##

#ADD NEW OFFICER
def addOfficer(request):
    if request.method == "POST":
        pass
    if 'roadGrievAdmin' in request.COOKIES:
        usrId = request.COOKIES['roadGrievAdmin']
        usr = Users.objects.get(id=usrId)
        roles = RoleTable.objects.all()
        con = {
            'user_object': usr,
            'id': int(usr.id),
            'name': str(usr.name),
            'user_type': str(usr.user_type),
            'roles': roles
        }
        return render(request, "roadGriev/addOfficer.html", con)
    return HttpResponse("Permission denied")


#SET HIERARCHY OF OFFICERS
def setHirarchy(request):
    usrId = request.COOKIES['roadGrievAdmin']
    usr = Users.objects.get(id=usrId)

    complaints = list(RoadComplaintToOfficer.objects.filter(officer_id=usr))

    con = {
        'user_object': usr,
        'id': int(usr.id),
        'name': str(usr.name),
        'user_type': str(usr.user_type)
    }
    return render(request, "roadGriev/setHirarchy.html", con)


#GET CURRENT HIERARCHY FROM DB
def getHierarchy(request):
    allH = RoleTable.objects.all()
    data = {
        'total': len(allH),
        'list': [
            {
                'role_name': str(i.role_name),
                'roleID': str(i.idName),
                'weight': int(i.weight)
            } for i in allH
        ]
    }
    return JsonResponse(data)

#SAVE CURRENT HIERARCHY
def applyHierarchy(request):
    if request.method == "POST":
        data = request.POST.getlist('roles[]')
        roles = data
        RoleTable.objects.all().delete()
        cnt = 0
        for i in roles:
            tid = i.replace(" ", "")
            temp = RoleTable(role_name=i, weight=cnt, idName=tid)
            cnt = cnt + 1
            temp.save()

        data = {
            'status': 0,
            'data': {
                'message': "Done"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)

#GET TYPES OF GRIEVANCES FROM DB
def TypesOfGrievances(request):
    if request.method == "GET":
        tpyes = TypesOfGrievancesModel.objects.all()
        if len(tpyes) > 0:
            data = {
                'status': 0,
                'data': [str(typ.title) for typ in tpyes]
            }
            return JsonResponse(data)
        data = {
            'status': 0,
            'data': [ "POT HOLES" ]
        }
        return JsonResponse(data)
    return


#FOR STORING LOCATION FOR DUPLICATE REQUESTS (ONLY FOR TESTING)
def Test(request):
    if request.method == "GET" and 'location_latitude' in request.GET and 'location_longitude' in request.GET:
        try:
            exloc = PreStoredLoc.objects.get(location_latitude=location_latitude, location_longitude=location_longitude)
            area = str(exloc.taluka)
            city = str(exloc.district)
            state = str(exloc.state)
        except Exception as e:
            try:
                locData = requests.get("http://nominatim.openstreetmap.org/reverse?format=json&zoom=19&lat="+location_latitude+"&lon="+location_longitude)
                locData = locData.json()
                print(locData)
                return JsonResponse(locData)
            except Exception as e:
                print("ERROR")
    return HttpResponse("ERROR")

def logout(request):
    if 'roadGrievUser' in request.COOKIES:
        response = redirect("../")
        response.delete_cookie('roadGrievUser')
        return response
    if 'roadGrievAdmin' in request.COOKIES:
        response = redirect("../")
        response.delete_cookie('roadGrievAdmin')
        return response
    return redirect("../")

##

#API LOGIN PART

##

def Login(request):
    if request.method == "GET":
        if 'email' in request.GET and 'password' in request.GET:
            email = request.GET['email']
            password = request.GET['password']
            print(email, password)
            try:
                tempUsr = Users.objects.get(email=email)
            except ObjectDoesNotExist:
                data = {
                    'status': 1,
                    'error' : {
                        'messages': "User not found"
                    }
                }
                return JsonResponse(data)
            if tempUsr.password == password:
                data = {
                    'status': 0,
                    'data' : {
                        'id': str(tempUsr.id),
                        'name': str(tempUsr.name),
                        'email': str(tempUsr.email),
                        'address': str(tempUsr.address),
                        'user_type': str(tempUsr.user_type)
                    }
                }
                return JsonResponse(data)
            data = {
                'status': 1,
                'error' : {
                    'messages': "Password did not matched"
                }
            }
            return JsonResponse(data)
        data = {
            'status': 1,
            'error' : {
                'messages': "Required Parameters not found"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)

def Signup(request):
    if request.method == "GET":
        if 'name' in request.GET and 'email' in request.GET and 'password' in request.GET and 'verified' in request.GET:
            name = request.GET['name']
            email = request.GET['email']
            address = ""
            password = request.GET['password']
            verified = int(request.GET['verified'])

            print(verified)

            try:
                tempUsr = Users.objects.get(email=email)
                data = {
                    'status': 1,
                    'error' : {
                        'messages': "User already exists"
                    }
                }
                return JsonResponse(data)
            except ObjectDoesNotExist:

                if verified == 0 :
                    otp = random.randint(1000, 9999)
                    #mail to user
                    if True:
                        mail(email, 'your otp : ' + str(otp), "Your Road Companian opt is " + str(otp))
                    print("mail sent")
                    data = {
                        'status': 2,
                        'data' : {
                            'otp': otp
                        }
                    }
                    return JsonResponse(data)

                else :
                    newUsr = Users(name=name, email=email, address=address, password=password)
                    newUsr.save()
                    print("user registered")

                    data = {
                        'status': 0,
                        'data' : {
                            'id': str(newUsr.id),
                            'name': str(newUsr.name),
                            'email': str(newUsr.email),
                            'address': str(newUsr.address),
                            'user_type': str(newUsr.user_type)
                        }
                    }
                    return JsonResponse(data)
        data = {
            'status': 1,
            'error' : {
                'messages': "Required Parameters not found"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)

def OfficerSignup(request):
    if request.method == "POST":
        if 'name' in request.POST and 'email' in request.POST and 'password' in request.POST and 'road_code' in request.POST and 'SrOfficer' in request.POST:
            if 'user_type' in request.POST and request.POST['user_type'] != 'user':
                name = request.POST['name']
                email = request.POST['email']
                password = request.POST['password']
                user_type = request.POST['user_type']
                road_code = request.POST['road_code']
                SrOfficer = request.POST['SrOfficer']

                if user_type == 'Section Officer' and road_code == '-1':
                    data = {
                        'status': 1,
                        'error' : {
                            'messages': "Section Officer must select a road"
                        }
                    }
                    return JsonResponse(data)

                if user_type != 'Chief Engineer' and SrOfficer == '-1':
                    data = {
                        'status': 1,
                        'error' : {
                            'messages': "Officer must select a Senior Officer"
                        }
                    }
                    return JsonResponse(data)

                try:
                    #check if user exists
                    tempUsr = Users.objects.get(email=email, user_type=user_type)
                    temp = Officers(officer_id=tempUsr)
                except ObjectDoesNotExist:
                    newUsr = Users(name=name, email=email, password=password, user_type=user_type)
                    newUsr.save()
                    if road_code != '-1':
                        road_code = RoadMapping.objects.get(road_code=road_code)
                        newOff = Officers(officer_id=newUsr, road_code=road_code)
                    else :
                        newOff = Officers(officer_id=newUsr)
                    newOff.save()

                    if SrOfficer != '-1':
                        SrOfficer = Users.objects.get(id=int(SrOfficer))
                        newConn = OfficersConnections(jr_officer_id=newUsr, sr_officer_id=SrOfficer)
                        newConn.save()

                    data = {
                        'status': 0,
                        'data' : {
                            'message': "Id : " + str(newOff.id)
                        }
                    }
                    return JsonResponse(data)

                data = {
                    'status': 1,
                    'error' : {
                        'messages': "User already exists"
                    }
                }
                return JsonResponse(data)
            data = {
                'status': 1,
                'error' : {
                    'messages': "Invalid user type"
                }
            }
            return JsonResponse(data)
        data = {
            'status': 1,
            'error' : {
                'messages': "Required Parameters not found"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)

#DISTANCE BETWEEN TWO LAT, LONG
#SET MIN_DISTANCE VARIABLE FOR MINIMUM DISTANCE
def Distance(lat1, lon1, lat2, lon2):
    try:
        R = 6373.0
        lat1 = radians(float(lat1))
        lon1 = radians(float(lon1))
        lat2 = radians(float(lat2))
        lon2 = radians(float(lon2))
        dlon = float(lon2 - lon1)
        dlat = float(lat2 - lat1)
        a = float(sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2)
        c = float(2 * atan2(sqrt(a), sqrt(1 - a)))
        finalD = float(R * c * 1000)
        return finalD
    except Exception as e:
        print(e)
        return MIN_DISTANCE * 2


def complaint(request):
    nm = []
    val = []
    for i in request.GET:
        nm.append(i)
        val.append(request.GET[i])
    postData = dict(zip(nm, val))
    condition = 'url' in postData and 'latitude' in postData and 'longitude' in postData and 'description' in postData and 'grievence' in postData and 'isemergency' in postData
    condition = condition and 'postedUserID' in postData and 'mode' in postData
    url = postData['url']
    tempUrl = url
    if condition:
        url = "https://ucarecdn.com" + postData['url'][32:]
        location_latitude = postData['latitude']
        location_longitude = postData['longitude']
        description = postData['description']
        road_type = "State Highway"#postData['roadtype']
        grievance = postData['grievence']
        isEmergency = int(postData['isemergency'])
        postedUserID = postData['postedUserID']
        mode = int(postData['mode'])

        try:
            apiResponse = requests.get("https://ncog.gov.in/RNB_mob_data/get_road?code=4862&lat="+str(location_latitude)+"&lon="+str(location_longitude))
            # apiResponse = requests.get("https://quiztime-induction.herokuapp.com/road_api/?lat="+str(location_latitude)+"&lon="+str(location_longitude))
            apiResponse = apiResponse.json()
            RESPONSE = False

            for response in apiResponse:
                distance_gps = float(response['distance'])
                print("distance : " + str(distance_gps))
                Response = response['uniqe_code']
                print(Response)
                try:
                    Response = list(RoadMapping.objects.filter(road_code=Response))[0]
                    if distance_gps >= 0.250:
                         data = {
                             'status': 1,
                             'error' : {
                                 'messages': "Your GPS location is inaccurate"
                             }
                         }
                         return JsonResponse(data)
                    RESPONSE = True
                    break
                except ObjectDoesNotExist:
                    pass


            if RESPONSE == False:
                data = {
                    'status': 1,
                    'error' : {
                        'messages': "Road not found"
                    }
                }
                return JsonResponse(data)


        #     distance_gps = float(apiResponse[0]['distance'])
        #     print("distance : " + str(distance_gps))
        #     if distance_gps >= 0.250:
        #          data = {
        #              'status': 1,
        #              'error' : {
        #                  'messages': "Your GPS location is inaccurate"
        #              }
        #          }
        #          return JsonResponse(data)
        #     apiResponse = apiResponse[0]['road_name']
        #     print(apiResponse)
        #     try:
        #         apiResponse = RoadMapping.objects.get(road_name=str(apiResponse))
        #     except ObjectDoesNotExist:
        #         distance_gps = float(apiResponse[1]['distance'])
        #         print("distance : " + str(distance_gps))
        #         if distance_gps >= 0.250:
        #              data = {
        #                  'status': 1,
        #                  'error' : {
        #                      'messages': "Your GPS location is inaccurate"
        #                  }
        #              }
        #              return JsonResponse(data)
        #         apiResponse = apiResponse[1]['road_name']
        #         print(apiResponse)
        #
        #
        except Exception as e:
            print(e)
            data = {
                'status': 1,
                'error' : {
                    'messages': str(e)
                }
            }
            return JsonResponse(data)

        try:
            officer = Officers.objects.get(road_code=Response)
            allComplaints = RoadComplaint.objects.filter(current_officer=officer.officer_id, grievence_type=grievance)
            print("all complaints : ")
            print(allComplaints)
        except ObjectDoesNotExist:
            data = {
                'status': 1,
                'error' : {
                    'messages': "Officer not found"
                }
            }
            return JsonResponse(data)

        if len(allComplaints) > 0:
            nearByComplaints = []
            for complaint in allComplaints:
                dis = Distance(complaint.location_latitude, complaint.location_longitude, location_latitude, location_longitude)
                print("distance : " + str(dis))
                if dis <= MIN_DISTANCE:
                    nearByComplaints.append([complaint, dis])

            print("nearByComplaints : ")
            print(nearByComplaints)

            if len(nearByComplaints) > 0:
                sortedNearByComplaint = sorted(nearByComplaints, key=itemgetter(1))
                nearestComplaint = sortedNearByComplaint[0][0]

                nearestComplaint.priority += 1
                nearestComplaint.save()
                print("nearest complaint : ")
                print(nearestComplaint)

                user = Users.objects.get(id=postedUserID)
                newCom = RoadComplaintPostedUser(complaint_id=nearestComplaint, user_id=user)
                newCom.save()

                if MAIL_ON:
                    htmlText = """
                        <html>
                            <head><title>Auto Generated Mail to field officer</title></head>
                            <body>
                                <div>
                                    <div>Hello '"""+ str(officer.officer_id.name) +"""',</div>
                                    <div><pre style="font-size: 15px;">	You have an complain of """ + grievance + """ at """ + road_type + """ filed on """ + str(newComplaint.time)[:-7] + """.</pre></div>
                                    <br><div>Thank You.</div>
                                </div>
                            </body>
                        </html>
                    """
                    mail(officer.officer_id.email, htmlText)

                data = {
                    'status': 0,
                    'data' : {
                        'id': str(newCom.id)
                    }
                }
                return JsonResponse(data)

        newComplaint = RoadComplaint(url=url, location_latitude=location_latitude, location_longitude=location_longitude, description=description, road_type=road_type, grievence_type=grievance, current_officer=officer.officer_id)
        newComplaint.save()

        user = Users.objects.get(id=postedUserID)
        newCom = RoadComplaintPostedUser(complaint_id=newComplaint, user_id=user)
        newCom.save()

        # try:
        newOffConn = RoadComplaintToOfficer(complaint_id=newComplaint, officer_id=officer.officer_id)
        newOffConn.save()
        nextOff = OfficersConnections.objects.get(jr_officer_id=officer.officer_id)
        newOffConn = RoadComplaintToOfficer(complaint_id=newComplaint, officer_id=nextOff.sr_officer_id)
        newOffConn.save()
        nextOff = OfficersConnections.objects.get(jr_officer_id=nextOff.sr_officer_id)
        newOffConn = RoadComplaintToOfficer(complaint_id=newComplaint, officer_id=nextOff.sr_officer_id)
        newOffConn.save()
        # except Exception as e:
        #     print(e)
        #     print("Error while sending data to higer officer")


        if MAIL_ON:
            htmlText = """
                <html>
                    <head><title>Auto Generated Mail to field officer</title></head>
                    <body>
                        <div>
                            <div>Hello '"""+ str(officer.officer_id.name) +"""',</div>
                            <div><pre style="font-size: 15px;">	You have an complain of """ + grievance + """ at """ + road_type + """ filed on """ + str(newComplaint.time)[:-7] + """.</pre></div>
                            <br><div>Thank You.</div>
                        </div>
                    </body>
                </html>
            """
            mail(officer.officer_id.email, htmlText)


        data = {
            'status': 0,
            'data' : {
                'id': str(newComplaint.id)
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Required parameters not found"
        }
    }
    return JsonResponse(data)

def ComplaintStatusChange(request):
    if request.method == "GET":
        if 'complaint_id' in request.GET and 'newStatus' in request.GET and 'officer_id' in request.GET:
            cid = request.GET['complaint_id']
            newStatus = request.GET['newStatus']
            oid = request.GET['officer_id']

            try:
                cd = RoadComplaint.objects.get(id=cid)
            except ObjectDoesNotExist:
                data = {
                    'status': 1,
                    'error' : {
                        'messages': "Complaint not found"
                    }
                }
                return JsonResponse(data)

            try:
                od = Users.objects.get(id=oid)
            except ObjectDoesNotExist:
                data = {
                    'status': 1,
                    'error' : {
                        'messages': "Officer not found"
                    }
                }
                return JsonResponse(data)

            cd.workStatus = newStatus
            cd.save()

            tUsers = list(RoadComplaintPostedUser.objects.filter(complaint_id=cd))

            if MAIL_ON == True:
                for usr in tUsers:
                    mail(usr.user_id.email, "Road Companian Grievence Status changed", "Hello User, Your Road Companian Grievence complaint status has been changed")

            data = {
                'status': 0,
                'data' : {
                    'messages': "Status changed"
                }
            }
            return JsonResponse(data)

        data = {
            'status': 1,
            'error' : {
                'messages': "Required parameters not found"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)
def UserComplaints(request):
    if request.method == "GET" and 'user_id' in request.GET:
        try:
            user_id = request.GET['user_id']
            uid = Users.objects.get(id=int(user_id))
            print("str : " + str(uid.name))
        except ObjectDoesNotExist:
            data = {
                'status': 1,
                'error' : {
                    'messages': "User not found"
                }
            }
            return JsonResponse(data)
    # try:
        coms = list(RoadComplaintPostedUser.objects.filter(user_id=uid))
        print(coms)
        data = {
            'status': 0,
            'data' : {
                'total': len(coms),
                'complaints': [
                    {
                        'id': int(cm.complaint_id.id),
                        'url': str(cm.complaint_id.url),
                        'location_latitude': cm.complaint_id.location_latitude,
                        'location_longitude': cm.complaint_id.location_longitude,
                        'description': str(cm.complaint_id.description),
                        'road_type': str(cm.complaint_id.road_type),
                        'grievence_type': str(cm.complaint_id.grievence_type),
                        'taluka': str(cm.complaint_id.area),
                        'district': str(cm.complaint_id.city),
                        'state': str(cm.complaint_id.state),
                        'workstatus': str(cm.complaint_id.workStatus),
                        'time': str(cm.complaint_id.time),
                        'isEmergency': int(cm.complaint_id.isEmergency) if cm.complaint_id.isEmergency is not None else str(0),
                        'comment': str(cm.complaint_id.comments),
                        'esitmated_time': str(cm.complaint_id.esitmated_time),
                        'current_officer': {
                            'id': int(cm.complaint_id.current_officer.id),
                            'name': str(cm.complaint_id.current_officer.name),
                            'email': str(cm.complaint_id.current_officer.email)
                        }
                    } for cm in coms
                ]
            }
        }
        return JsonResponse(data)
    # except Exception as e:
        print(e)
        data = {
            'status': 1,
            'error' : {
                'messages': "Unknown error"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)

def SortedData(request):
    if request.method == "GET" and 'taluka' in request.GET:
        taluka = request.GET['taluka']
        try:
            emerComs = RoadComplaint.objects.filter(area=taluka, isEmergency=1)
        except Exception as e:
            data = {
                'status': 1,
                'error' : {
                    'messages': "Unknown error"
                }
            }
            return JsonResponse(data)
        try:
            mstRecent = RoadComplaint.objects.filter(area=taluka).order_by('-time')
        except Exception as e:
            data = {
                'status': 1,
                'error' : {
                    'messages': "Unknown error"
                }
            }
            return JsonResponse(data)
        try:
            penComs = RoadComplaint.objects.filter(area=taluka, workStatus="pending")
        except Exception as e:
            data = {
                'status': 1,
                'error' : {
                    'messages': "Unknown error"
                }
            }
            return JsonResponse(data)
        try:
            valComs = RoadComplaint.objects.filter(area=taluka, workStatus="approved")
        except Exception as e:
            data = {
                'status': 1,
                'error' : {
                    'messages': "Unknown error"
                }
            }
            return JsonResponse(data)
        try:
            ipComs = RoadComplaint.objects.filter(area=taluka, workStatus="in progress")
        except Exception as e:
            data = {
                'status': 1,
                'error' : {
                    'messages': "Unknown error"
                }
            }
            return JsonResponse(data)
        try:
            cComs = RoadComplaint.objects.filter(area=taluka, workStatus="complete")
        except Exception as e:
            data = {
                'status': 1,
                'error' : {
                    'messages': "Unknown error"
                }
            }
            return JsonResponse(data)

        data = {
            'Grievences': [
                {
                    'title': 'Emergency Complaints',
                    'data': [
                        {
                            'id': int(cm.id),
                            'url': str(cm.url),
                            'location_latitude': cm.location_latitude,
                            'location_longitude': cm.location_longitude,
                            'description': str(cm.description),
                            'road_type': str(cm.road_type),
                            'grievence_type': str(cm.grievence_type),
                            'taluka': str(cm.area),
                            'district': str(cm.city),
                            'state': str(cm.state),
                            'workstatus': str(cm.workStatus),
                            'time': str(cm.time),
                            'isEmergency': int(cm.isEmergency),
                            'comment': str(cm.comments),
                            'esitmated_time': str(cm.esitmated_time),
                            'current_officer': {
                                'id': int(cm.current_officer.id),
                                'name': str(cm.current_officer.name),
                                'email': str(cm.current_officer.email)
                            }
                        } for cm in emerComs
                    ]
                },
                {
                    'title': 'Most Recent Complaints',
                    'data': [
                        {
                            'id': int(cm.id),
                            'url': str(cm.url),
                            'location_latitude': cm.location_latitude,
                            'location_longitude': cm.location_longitude,
                            'description': str(cm.description),
                            'road_type': str(cm.road_type),
                            'grievence_type': str(cm.grievence_type),
                            'taluka': str(cm.area),
                            'district': str(cm.city),
                            'state': str(cm.state),
                            'workstatus': str(cm.workStatus),
                            'time': str(cm.time),
                            'isEmergency': int(cm.isEmergency),
                            'comment': str(cm.comments),
                            'esitmated_time': str(cm.esitmated_time),
                            'current_officer': {
                                'id': int(cm.current_officer.id),
                                'name': str(cm.current_officer.name),
                                'email': str(cm.current_officer.email)
                            }
                        } for cm in mstRecent
                    ]
                },
                {
                    'title': 'Pending Complaints',
                    'data': [
                        {
                            'id': int(cm.id),
                            'url': str(cm.url),
                            'location_latitude': cm.location_latitude,
                            'location_longitude': cm.location_longitude,
                            'description': str(cm.description),
                            'road_type': str(cm.road_type),
                            'grievence_type': str(cm.grievence_type),
                            'taluka': str(cm.area),
                            'district': str(cm.city),
                            'state': str(cm.state),
                            'workstatus': str(cm.workStatus),
                            'time': str(cm.time),
                            'isEmergency': int(cm.isEmergency),
                            'comment': str(cm.comments),
                            'esitmated_time': str(cm.esitmated_time),
                            'current_officer': {
                                'id': int(cm.current_officer.id),
                                'name': str(cm.current_officer.name),
                                'email': str(cm.current_officer.email)
                            }
                        } for cm in penComs
                    ]
                },
                {
                    'title': 'Validated Complaints',
                    'data': [
                        {
                            'id': int(cm.id),
                            'url': str(cm.url),
                            'location_latitude': cm.location_latitude,
                            'location_longitude': cm.location_longitude,
                            'description': str(cm.description),
                            'road_type': str(cm.road_type),
                            'grievence_type': str(cm.grievence_type),
                            'taluka': str(cm.area),
                            'district': str(cm.city),
                            'state': str(cm.state),
                            'workstatus': str(cm.workStatus),
                            'time': str(cm.time),
                            'isEmergency': int(cm.isEmergency),
                            'comment': str(cm.comments),
                            'esitmated_time': str(cm.esitmated_time),
                            'current_officer': {
                                'id': int(cm.current_officer.id),
                                'name': str(cm.current_officer.name),
                                'email': str(cm.current_officer.email)
                            }
                        } for cm in valComs
                    ]
                },
                {
                    'title': 'In Progress Complaints',
                    'data': [
                        {
                            'id': int(cm.id),
                            'url': str(cm.url),
                            'location_latitude': cm.location_latitude,
                            'location_longitude': cm.location_longitude,
                            'description': str(cm.description),
                            'road_type': str(cm.road_type),
                            'grievence_type': str(cm.grievence_type),
                            'taluka': str(cm.area),
                            'district': str(cm.city),
                            'state': str(cm.state),
                            'workstatus': str(cm.workStatus),
                            'time': str(cm.time),
                            'isEmergency': int(cm.isEmergency),
                            'comment': str(cm.comments),
                            'esitmated_time': str(cm.esitmated_time),
                            'current_officer': {
                                'id': int(cm.current_officer.id),
                                'name': str(cm.current_officer.name),
                                'email': str(cm.current_officer.email)
                            }
                        } for cm in ipComs
                    ]
                },
                {
                    'title': 'Completed Complaints',
                    'data': [
                        {
                            'id': int(cm.id),
                            'url': str(cm.url),
                            'location_latitude': cm.location_latitude,
                            'location_longitude': cm.location_longitude,
                            'description': str(cm.description),
                            'road_type': str(cm.road_type),
                            'grievence_type': str(cm.grievence_type),
                            'taluka': str(cm.area),
                            'district': str(cm.city),
                            'state': str(cm.state),
                            'workstatus': str(cm.workStatus),
                            'time': str(cm.time),
                            'isEmergency': int(cm.isEmergency),
                            'comment': str(cm.comments),
                            'esitmated_time': str(cm.esitmated_time),
                            'current_officer': {
                                'id': int(cm.current_officer.id),
                                'name': str(cm.current_officer.name),
                                'email': str(cm.current_officer.email)
                            }
                        } for cm in cComs
                    ]
                }
            ]
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)


def RequestsSort(request):
    if request.method == "GET":
        if 'road_type' in request.GET and 'grievance_type' in request.GET and 'status_type' and request.GET and 'startDate' in request.GET and 'endDate' in request.GET and 'officer_id' in request.GET and 'emergencyOn' in request.GET:
            print(request.GET['grievance_type'])
            uid = Users.objects.get(id=request.GET['officer_id'])
            print(uid)
            print(request.GET['road_type'])
            cmps= RoadComplaint.objects.filter(road_type=request.GET['road_type'], grievence_type=request.GET['grievance_type'], workStatus=request.GET['status_type'], current_officer=uid, isEmergency=int(request.GET['emergencyOn']) ).order_by('-priority')
            print("-->> " + str(len(cmps)))
            for i in cmps:
                print(i.road_type)
            final = []
            strt = datetime.strptime(request.GET['startDate'], '%Y-%m-%d').date()
            endd = datetime.strptime(request.GET['endDate'], '%Y-%m-%d').date()
            print(strt, endd)
            for cm in cmps:
                if strt <= cm.time.date() < endd:
                    final.append(cm)

            data = {
                'status': 0,
                'len': len(final),
                'title': 'Complaints',
                'data': [
                    {
                        'id': int(cm.id),
                        'url': str(cm.url),
                        'location_latitude': cm.location_latitude,
                        'location_longitude': cm.location_longitude,
                        'description': str(cm.description),
                        'road_type': str(cm.road_type),
                        'grievence_type': str(cm.grievence_type),
                        'taluka': str(cm.area),
                        'district': str(cm.city),
                        'state': str(cm.state),
                        'workstatus': str(cm.workStatus),
                        'time': str(cm.time),
                        'isEmergency': int(cm.isEmergency),
                        'comment': str(cm.comments),
                        'esitmated_time': str(cm.esitmated_time),
                        'current_officer': {
                            'id': int(cm.current_officer.id),
                            'name': str(cm.current_officer.name),
                            'email': str(cm.current_officer.email)
                        }
                    } for cm in final
                ]
            }
            return JsonResponse(data)
    data = {
        'status': 1,
        'error': {
            'message': "access denied"
        }
    }
    return JsonResponse(data)

    # if request.method == "GET":
    #     if 'road_type' in request.GET and 'grievance_type' in request.GET and 'status_type' and request.GET and 'startDate' in request.GET and 'endDate' in request.GET and 'officer_id' in request.GET:
    #         uid = Users.objects.get(id=request.GET['officer_id'])
    #         print(request.GET['road_type'], request.GET['grievance_type'], request.GET['status_type'])
    #         cmps = RoadComplaint.objects.filter(
    #             road_type = request.GET['road_type'] if request.GET['road_type'] != "none" else None,
    #             grievence_type = request.GET['grievance_type'] if request.GET['grievance_type'] != "none" else None,
    #             workStatus = request.GET['status_type'] if request.GET['status_type'] != "none" else None,
    #             current_officer=uid
    #         )
    #
    #         final = []
    #         try:
    #             strt = datetime.strptime(request.GET['startDate'], '%Y-%m-%d').date()
    #             endd = datetime.strptime(request.GET['endDate'], '%Y-%m-%d').date()
    #
    #             for cm in cmps:
    #                 if strt >= cm.time.date() and endd <= cm.time.date():
    #                     final.append(cm)
    #         except Exception as e:
    #             for cm in cmps:
    #                 final.append(cm)
    #             print("Exception ouccured in filtering requests by time")
    #
    #
    #         data = {
    #             'status': 0,
    #             'len': len(final),
    #             'title': 'Complaints',
    #             'data': [
    #                 {
    #                     'id': int(cm.id),
    #                     'url': str(cm.url),
    #                     'location_latitude': cm.location_latitude,
    #                     'location_longitude': cm.location_longitude,
    #                     'description': str(cm.description),
    #                     'road_type': str(cm.road_type),
    #                     'grievence_type': str(cm.grievence_type),
    #                     'taluka': str(cm.area),
    #                     'district': str(cm.city),
    #                     'state': str(cm.state),
    #                     'workstatus': str(cm.workStatus),
    #                     'time': str(cm.time),
    #                     'isEmergency': int(cm.isEmergency),
    #                     'comment': str(cm.comments),
    #                     'esitmated_time': str(cm.esitmated_time),
    #                     'current_officer': {
    #                         'id': int(cm.current_officer.id),
    #                         'name': str(cm.current_officer.name),
    #                         'email': str(cm.current_officer.email)
    #                     }
    #                 } for cm in final
    #             ]
    #         }
    #         return JsonResponse(data)
    # data = {
    #     'status': 1,
    #     'error': {
    #         'message': "access denied"
    #     }
    # }
    # return JsonResponse(data)

def  AllComplaints(request):
    allc = RoadComplaint.objects.all()

    data = {
        'title': 'All Complaints',
        'total': len(allc),
        'data': [
            {
                'id': int(cm.id),
                'url': str(cm.url),
                'location_latitude': cm.location_latitude,
                'location_longitude': cm.location_longitude,
                'description': str(cm.description),
                'road_type': str(cm.road_type),
                'grievence_type': str(cm.grievence_type),
                'area': str(cm.area),
                'city': str(cm.city),
                'state': str(cm.state),
                'workStatus': str(cm.workStatus),
                'time': str(cm.time),
                'isEmergency': int(cm.isEmergency),
                'comment': str(cm.comments),
                'esitmated_time': str(cm.esitmated_time),
                'current_officer': {
                    'id': int(cm.current_officer.id),
                    'name': str(cm.current_officer.name),
                    'email': str(cm.current_officer.email)
                }
            } for cm in allc
        ]
    }
    return JsonResponse(data)

def ArchiveData(request):
    if request.method == "GET":
        if 'date' in request.GET:
            date = request.GET['date']

            allComs = RoadComplaint.objects.filter(workStatus="complete")
            for cm in allComs:
                if cm.time.date() < datetime.strptime(date, '%Y-%m-%d').date():
                    url = str(cm.url)
                    location_latitude = str(cm.location_latitude)
                    location_longitude = str(cm.location_longitude)
                    description = str(cm.description)
                    road_type = str(cm.road_type)
                    grievence_type = str(cm.grievence_type)
                    area = str(cm.area)
                    city = str(cm.city)
                    state = str(cm.state)
                    isEmergency = str(cm.isEmergency)
                    workStatus = str(cm.workStatus)
                    current_officer = cm.current_officer
                    time = cm.time
                    comments = str(cm.comments)
                    esitmated_time = cm.esitmated_time
                    cmp =  ArchivedRoadComplaint(
                        url = url,
                        location_latitude = location_latitude,
                        location_longitude = location_longitude,
                        description = description,
                        road_type = road_type,
                        grievence_type = grievence_type,
                        area = area,
                        city = city,
                        state = state,
                        isEmergency = isEmergency,
                        workStatus = workStatus,
                        current_officer = current_officer,
                        time = time,
                        comments = comments,
                        esitmated_time = esitmated_time
                    )
                    cmp.save()
                    #cm.delete()
            data = {
                'status': 0,
                'data': {
                    'message': "data"
                }
            }
            return JsonResponse(data)

        data = {
            'status': 1,
            'error': {
                'message': "date required"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error': {
            'message': "access denied"
        }
    }
    return JsonResponse(data)

def AddCatagory(request):
    if request.method == "POST":
        if 'name' in request.POST:
            newType = TypesOfGrievancesModel(title=request.POST['name'])
            newType.save()
            data = {
                'status': 0,
                'data': {
                    'message': "changed"
                }
            }
            return JsonResponse(data)
        data = {
            'status': 1,
            'error': {
                'message': "name required"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error': {
            'message': "access denied"
        }
    }
    return JsonResponse(data)

def deleteCatagory(request):
    if request.method == "POST":
        if 'name' in request.POST:
            try:
                newType = TypesOfGrievancesModel(title=request.POST['name'])
                newType.delete()
                data = {
                    'status': 0,
                    'data': {
                        'message': "changed"
                    }
                }
                return JsonResponse(data)
            except Exception as e:
                data = {
                    'status': 1,
                    'error': {
                        'message': "Unknown ERROR"
                    }
                }
                return JsonResponse(data)
    data = {
        'status': 1,
        'error': {
            'message': "access denied"
        }
    }
    return JsonResponse(data)

def Catagory(request):
    if 'roadGrievAdmin' in request.COOKIES:
        cats = TypesOfGrievancesModel.objects.all()
        print(len(cats))
        usrId = request.COOKIES['roadGrievAdmin']
        usr = Users.objects.get(id=usrId)
        con = {
            'user_object': usr,
            'id': int(usr.id),
            'name': str(usr.name),
            'user_type': str(usr.user_type),
            'cats': [
                c for c in cats
            ]
        }
        return render(request, 'roadGriev/AddCatagory.html', )
    return redirect("../")

def comment(request):
    if request.method == "POST":
        cmp = RoadComplaint.objects.get(id=request.POST['complaint_id'])
        cmp.comments = request.POST['comment']
        cmp.save()
        data = {
            'status': 0,
            'data': {
                'message': "done"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error': {
            'message': "Unknown ERROR"
        }
    }
    return JsonResponse(data)


def EmergencyStatusChanged(request):
    if request.method == "GET":
        if 'complaint_id' in request.GET and 'newValue' in request.GET:
            cid = request.GET['complaint_id']
            newValue = request.GET['newValue']

            try:
                cd = RoadComplaint.objects.get(id=cid)
            except ObjectDoesNotExist:
                data = {
                    'status': 1,
                    'error' : {
                        'messages': "Complaint not found"
                    }
                }
                return JsonResponse(data)

            cd.isEmergency = newValue
            cd.save()

            data = {
                'status': 0,
                'data' : {
                    'messages': "Status changed"
                }
            }
            return JsonResponse(data)

        data = {
            'status': 1,
            'error' : {
                'messages': "Required parameters not found"
            }
        }
        return JsonResponse(data)
    data = {
        'status': 1,
        'error' : {
            'messages': "Access denied"
        }
    }
    return JsonResponse(data)


def getRoadsList(request):
    roads = RoadMapping.objects.all()

    data = {
        'list': [
            {
                'id': road.id,
                'name': road.road_name,
                'code': road.road_code
            } for road in roads
        ]
    }

    return JsonResponse(data)

def getSrOfficerList(request):
    if request.method == "GET":
        if 'name' in request.GET:
            name = request.GET['name']
            print(name)
            try:
                officerPost = RoleTable.objects.get(role_name=name)
                higherOfficer = RoleTable.objects.get(weight=(officerPost.weight + 1))
            except ObjectDoesNotExist:
                data = {
                    "error": "Officer not found"
                }
                return JsonResponse(data)

            allOfficer = Users.objects.filter(user_type=higherOfficer.role_name)

            data = {
                'list': [
                    {
                        'id': officer.id,
                        'name': officer.name,
                    } for officer in allOfficer
                ]
            }

            return JsonResponse(data)


    data = {
        "error": "invalid request"
    }

    return JsonResponse(data)
