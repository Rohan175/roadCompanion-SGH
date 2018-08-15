from django.http import HttpResponse
import json
from .forms import *
from django.core.exceptions import ObjectDoesNotExist
from .models import *
from .damageDetection import *
import logging
from django.shortcuts import render, redirect
from . import damageDetection
from django.template import RequestContext

API_KEYS = [
    '2901',
    'HELLOWORLD'
]


def index(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'parameters required',
                        'message': 'you have not given any kind of filter'
                    }
                ]
            }
            #api key not found
            return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def user(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            if request.method == 'POST':
                reg = Register(request.POST)
                if reg.is_valid():
                    name = reg.cleaned_data['name']
                    email = reg.cleaned_data['email']
                    password = reg.cleaned_data['password']

                    try:
                        mail = Users.objects.get(email=email)
                        data = {
                            'status': 400,
                            'errors': [
                                {
                                    'title': 'Email already exists',
                                    'message': 'Given mail is already exists. Please check if you already signed up.'
                                }
                            ]
                        }
                        return HttpResponse(json.dumps(data), content_type="application/json")
                    except ObjectDoesNotExist:
                        new_usr = Users(name=name, email=email, password=password)
                        new_usr.save()
                        data = {
                            'status': 200,
                            'success': {
                                'title': 'user registered',
                                'message': 'Now you can login with your account'
                            }
                        }
                        return HttpResponse(json.dumps(data), content_type="application/json")
                    data = {
                        'status': 400,
                        'errors': [
                            {
                                'title': 'Unknown error',
                                'message': 'Please try again leter'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json")
                #invalid form.....................................................................................
                data = {
                    'status': 400,
                    'errors': [
                        {
                            'title': 'From data are invalid.',
                            'message': 'Please check your data'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'parameters required',
                        'message': 'you have not given any kind of filter'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)


def searchUser(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            rec = request.GET
            if 'id' in rec:
                try:
                    uid = int(request.GET['id'])
                    usr = Users.objects.get(id=uid)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'User not found',
                                'message': 'Please check user id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                try:
                    roads = Roads.objects.filter(postedUser_id=int(request.GET['id']))
                except ObjectDoesNotExist:
                    data = {
                        'user_id': str(usr.id),
                        'name': str(usr.name),
                        'email': str(usr.mail),
                        'type': str(usr.usr_type),
                        'requested_roads': {
                            'total': 0,
                            'roads': []
                        }
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json")
                data = {
                    'user_id': str(usr.id),
                    'name': str(usr.name),
                    'email': str(usr.name),
                    'type': str(usr.usr_type),
                    'requested_roads': {
                        'total': len(roads),
                        'roads': [
                            {
                                'id': int(road.id),
                                'url': str(road.url),
                                'name': str(road.name),
                                'description': str(road.description),
                                'location': {
                                    'longitude': float(road.locLong),
                                    'latitude': float(road.locLat)
                                },
                                'field_officer_id': int(road.fieldOfficer),
                                'road_type': str(road.roadType),
                                'votes': int(road.votes),
                                'work_status': str(road.workStatus)
                            } for road in roads
                        ]
                    }
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            elif 'name' in rec:
                try:
                    nm = request.GET['name']
                    usr = Users.objects.filter(name=nm)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'User not found',
                                'message': 'Please check user id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                data = {
                    'total_user_found': len(usr),
                    'data': [
                        {
                            'user_id': str(u.id),
                            'name': str(u.name),
                            'email': str(u.email),
                            'type': str(u.usr_type),
                        } for u in usr
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            elif 'email' in rec:
                try:
                    nm = request.GET['email']
                    usr = Users.objects.filter(email=nm)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'User not found',
                                'message': 'Please check user id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                data = {
                    'total_user_found': len(usr),
                    'data': [
                        {
                            'user_id': str(u.id),
                            'name': str(u.name),
                            'email': str(u.email),
                            'type': str(u.usr_type),
                        } for u in usr
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")

            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'Parameters required',
                        'message': 'Search user by id, name or email'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        #else......
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def updateUser(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            if 'id' in request.GET:
                try:
                    usr = Users.objects.get(id=request.GET['id'])
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'User not found',
                                'message': 'Please check user id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)
                if request.method == "POST":
                    try:
                        if 'name' in request.POST:
                            usr.name = request.POST['name']
                            usr.save()
                        if 'email' in request.POST:
                            usr.email = request.POST['email']
                            usr.save()
                    except Exception as e:
                        data = {
                            'status': 400,
                            'errors': [
                                {
                                    'title': 'Data error',
                                    'message': str(e)
                                }
                            ]
                        }
                        return HttpResponse(json.dumps(data), content_type="application/json")
                    data = {
                        'status': 200,
                        'success': [
                            {
                                'title': 'Data updated',
                                'message': 'Value is changed now'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json")
                data = {
                    'status': 400,
                    'errors': [
                        {
                            'title': 'Data error',
                            'message': 'Nothing found to change'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'parameters required',
                        'message': 'you have not given any kind of filter'
                    }
                ]
            }
            #api key not found
            return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def road(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            if request.method == "POST":
                new_rd = Road(request.POST)
                if new_rd.is_valid():
                    url = new_rd.cleaned_data['url']
                    name = new_rd.cleaned_data['name']
                    roadType = new_rd.cleaned_data['roadType']
                    description = new_rd.cleaned_data['description']
                    locLong = new_rd.cleaned_data['locLong']
                    locLat = new_rd.cleaned_data['locLat']
                    fieldOfficer = new_rd.cleaned_data['fieldOfficer']
                    postedUser = new_rd.cleaned_data['postedUser']

                    try:
                        usr = Users.objects.get(id=postedUser)
                    except ObjectDoesNotExist:
                        data = {
                            'status': 404,
                            'errors': [
                                {
                                    'title': 'Invalid User ID',
                                    'message': 'Please check user id is correct or not.'
                                }
                            ]
                        }
                        return HttpResponse(json.dumps(data), content_type="application/json", status=404)
                    try:
                        fieldOf = FieldOff.objects.get(id=fieldOfficer)
                    except ObjectDoesNotExist:
                        data = {
                            'status': 404,
                            'errors': [
                                {
                                    'title': 'Invalid Field Officer ID',
                                    'message': 'Please check field officer id is corrector not.'
                                }
                            ]
                        }
                        return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                    if is_valid_road(url=url):
                        rd = Roads(url=url, name=name, roadType=roadType, description=description, locLong=locLong, locLat=locLat, postedUser=usr, fieldOfficer=fieldOf)
                        rd.save()
                    else:
                        data = {
                            'status': 400,
                            'errors': [
                                {
                                    'title': 'No damage found',
                                    'message': 'No damage detected in given image. Please try with other image'
                                }
                            ]
                        }
                        return HttpResponse(json.dumps(data), content_type="application/json")

                    data = {
                        'status': 200,
                        'success': {
                            'title': 'road details registered',
                            'message': 'done.'
                        }
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json")
                #invalid form.....................................................................................
                data = {
                    'status': 400,
                    'errors': [
                        {
                            'title': 'From data are invalid.',
                            'message': 'Please check your data'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def roadSearch(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            rec = request.GET
            if 'id' in rec:
                try:
                    uid = int(request.GET['id'])
                    rd = Roads.objects.get(id=uid)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Road not found',
                                'message': 'Please check road id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                data = {
                    'road_id': str(rd.id),
                    'name': str(rd.name),
                    'url': str(rd.url),
                    'description': str(rd.description),
                    'location': {
                        'longitude': float(rd.locLong),
                        'latitude': float(rd.locLat)
                    },
                    'field_officer_id': int(rd.fieldOfficer),
                    'posted_user_id': int(rd.postedUser_id),
                    'road_type': str(rd.roadType),
                    'votes': int(rd.votes),
                    'work_status': str(rd.workStatus)
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            elif 'name' in rec:
                try:
                    rname = request.GET['name']
                    rd = Roads.objects.get(name=rname)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Road not found',
                                'message': 'Please check road id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                data = {
                    'road_id': str(rd.id),
                    'name': str(rd.name),
                    'url': str(rd.url),
                    'description': str(rd.description),
                    'location': {
                        'longitude': float(rd.locLong),
                        'latitude': float(rd.locLat)
                    },
                    'field_officer_id': int(rd.fieldOfficer),
                    'posted_user_id': int(rd.postedUser_id),
                    'road_type': str(rd.roadType),
                    'votes': int(rd.votes),
                    'work_status': str(rd.workStatus)
                }
                return HttpResponse(json.dumps(data), content_type="application/json")

            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'Parameters required',
                        'message': 'Search user by id, name or email'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        #else......
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def updateRoad(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            if 'id' in request.GET:
                try:
                    rd = Roads.objects.get(id=request.GET['id'])
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Road not found',
                                'message': 'Please check road id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)
                if request.method == "POST":
                    try:
                        if 'name' in request.POST:
                            rd.name = request.POST['name']
                            rd.save()
                        if 'url' in request.POST:
                            rd.url = request.POST['url']
                            rd.save()
                        if 'description' in request.POST:
                            rd.description = request.POST['description']
                            rd.save()
                        if 'longitude' in request.POST:
                            rd.locLong = request.POST['longitude']
                            rd.save()
                        if 'latitude' in request.POST:
                            rd.locLat = request.POST['latitude']
                            rd.save()
                        if 'road_type' in request.POST:
                            rd.roadType = request.POST['road_type']
                            rd.save()
                        if 'work_status' in request.POST:
                            rd.workStatus = request.POST['work_status']
                            rd.save()
                    except Exception as e:
                        data = {
                            'status': 400,
                            'errors': [
                                {
                                    'title': 'Data error',
                                    'message': str(e)
                                }
                            ]
                        }
                        return HttpResponse(json.dumps(data), content_type="application/json")
                    data = {
                        'status': 200,
                        'success': [
                            {
                                'title': 'Data updated',
                                'message': 'Value is changed now'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json")
                data = {
                    'status': 400,
                    'errors': [
                        {
                            'title': 'Data error',
                            'message': 'Nothing found to change'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'parameters required',
                        'message': 'you have not given any kind of filter'
                    }
                ]
            }
            #api key not found
            return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def upVote(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            if 'id' in request.GET:
                try:
                    rd = Roads.objects.get(id=request.GET['id'])
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Road not found',
                                'message': 'Please check road id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                if rd.hirWeight >= 2:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Can not go more',
                                'message': 'It\'s already high'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)
                rd.hirWeight = rd.hirWeight + 1
                rd.save()

                data = {
                    'status': 200,
                    'success': [
                        {
                            'title': 'Data updated',
                            'message': 'Value is changed now'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")

            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'parameters required',
                        'message': 'you have not given road id'
                    }
                ]
            }
            #api key not found
            return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def downVote(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            if 'id' in request.GET:
                try:
                    rd = Roads.objects.get(id=request.GET['id'])
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Road not found',
                                'message': 'Please check road id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                if rd.votes == 0:
                    data = {
                        'status': 400,
                        'errors': [
                            {
                                'title': 'Road has 0 votes',
                                'message': 'you can not down vote more'
                            }
                        ]
                    }
                    #api key not found
                    return HttpResponse(json.dumps(data), content_type="application/json")
                rd.votes = rd.votes - 1
                rd.save()

                data = {
                    'status': 200,
                    'success': [
                        {
                            'title': 'Data updated',
                            'message': 'Value is changed now'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")

            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'parameters required',
                        'message': 'you have not given road id'
                    }
                ]
            }
            #api key not found
            return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def officer(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            rec = request.GET
            if 'id' in rec:
                try:
                    uid = int(request.GET['id'])
                    usr = FieldOff.objects.get(id=uid)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Officer not found',
                                'message': 'Please check officer id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)
                data = {
                    'officer_id': str(usr.id),
                    'name': str(usr.name),
                    'email': str(usr.email),
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            elif 'name' in rec:
                try:
                    nm = request.GET['name']
                    usr = FieldOff.objects.filter(name=nm)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Officer not found',
                                'message': 'Please check officer id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                data = {
                    'total_officer_found': len(usr),
                    'data': [
                        {
                            'officer_id': str(u.id),
                            'name': str(u.name),
                            'email': str(u.email)
                        } for u in usr
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            elif 'email' in rec:
                try:
                    nm = request.GET['email']
                    usr = FieldOff.objects.filter(email=nm)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Officer not found',
                                'message': 'Please check officer id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)

                data = {
                    'total_officer_found': len(usr),
                    'data': [
                        {
                            'officer_id': str(u.id),
                            'name': str(u.name),
                            'email': str(u.email)
                        } for u in usr
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")

            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'Parameters required',
                        'message': 'Search user by id, name or email'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        #else......
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def roadAdmins(request):
    if 'key' in request.GET:
        if request.GET['key'] in API_KEYS:
            rec = request.GET
            if 'id' in rec:
                try:
                    uid = int(request.GET['id'])
                    usr = FieldOff.objects.get(id=uid)
                except ObjectDoesNotExist:
                    data = {
                        'status': 404,
                        'errors': [
                            {
                                'title': 'Officer not found',
                                'message': 'Please check officer id'
                            }
                        ]
                    }
                    return HttpResponse(json.dumps(data), content_type="application/json", status=404)
                data = {
                    'officer_id': str(usr.id),
                    'name': str(usr.name),
                    'email': str(usr.email),
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            data = {
                'status': 404,
                'errors': [
                    {
                        'title': 'Id not entered',
                        'message': 'Please enter officer id'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json", status=404)
        #else......
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Invalid API key',
                    'message': 'You entered worng API key.'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied", status=403)

def login(request):
    if request.method == 'GET':
        email = request.GET['email']
        password = request.GET['password']

        try:
            usr = Users.objects.get(email=email)
        except ObjectDoesNotExist:
            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'User is not exists',
                        'message': 'Check your email'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        if usr.password == password:
            data = {
                'status': 200,
                'data' : {
                    'id': str(usr.id),
                    'name': str(usr.name),
                    'email': str(usr.email),
                    'type': str(usr.usr_type)
                }
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        data = {
            'status': 400,
            'errors': [
                {
                    'title': 'Incorrect password',
                    'message': 'Check your password'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied")

def Signup(request):
    if request.method == 'GET':
        email = request.GET['email']
        name = request.GET['name']
        password = request.GET['password']

        try:
            usr = Users.objects.get(email=email)
            data = {
                'status': 400,
                'errors': [
                    {
                        'title': 'User already exists',
                        'message': 'Enter different email'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        except ObjectDoesNotExist:
            u = Users(name=name, email=email, password=password)
            u.save()
        data = {
            'status': 200,
            'data': [
                {
                    'title': 'User registered',
                    'message': 'Now you can login'
                }
            ]
        }
        return HttpResponse(json.dumps(data), content_type="application/json")
    return HttpResponse("Access Denied")


import math

def Distance(lat1, long1, lat2, long2):
    d = 0
    R = 6378.137
    dlat = lat2 * math.pi / 180 - lat1 * math.pi / 180
    dlong = long2 * math.pi / 180 - long1 * math.pi / 180

    a = math.sin(dlat / 2) * math.cos(dlat / 2) + math.cos(lat1 * math.pi / 180) * math.cos(lat2 * math.pi / 180) * math.sin(dlong/2) * math.sin(dlong/2);
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1-a))
    return R * c * 1000

def roadPost(request):
    if request.method == 'GET':
        if 'url' in request.GET:
            url = request.GET['url']
        if 'description' in request.GET:
            description = request.GET['description']
        if 'locLong' in request.GET:
            locLong = request.GET['locLong']
        if 'locLat' in request.GET:
            locLat = request.GET['locLat']
        if 'area' in request.GET:
            area = request.GET['area']
        if 'city' in request.GET:
            city = request.GET['city']
        if 'state' in request.GET:
            state = request.GET['state']
        if 'roadType' in request.GET:
            roadType = request.GET['roadType']
        if 'GrievType' in request.GET:
            GrievType = request.GET['GrievType']
        if 'cat' in request.GET:
            cat = request.GET['cat']
        if 'postedUser' in request.GET:
            postedUser = request.GET['postedUser']


        if pa(url) != 1:
            data = {
                'status': 404,
                'errors': [
                    {
                        'title': 'damage not detected',
                        'message': 'Please take another picture'
                    }
                ]
            }
            return HttpResponse(json.dumps(data), content_type="application/json", status=404)

        temp = Roads.objects.filter(area=area, city=city, state=state, roadType=roadType, GrievType=GrievType, cat=cat)

        if len(temp) > 0:
            dis = []
            for rp in temp:
                if Distance(float(locLat), float(locLong), float(rp.locLat), float(rp.locLong)) <= 500:
                    dis.append([rp, Distance(float(locLat), float(locLong), float(rp.locLat), float(rp.locLong))])

            li = sorted(dis, key=lambda x: x[1], reverse=False)

            roadt = li[0][0] #nearest neighbour
            try:
                tusr = Users.objects.get(id=postedUser)
                rp = PostUsers(roadId=roadt, usrId=tusr)
                rp.save()
            except ObjectDoesNotExist:
                data = {
                    'status': 404,
                    'errors': [
                        {
                            'title': 'User not found',
                            'message': 'Please check user id'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json", status=404)
            data = {
                'status': 200,
                'data': {
                    'title': 'successfully registered',
                }
            }
            return HttpResponse(json.dumps(data), content_type="application/json", status=404)

        try:
            fo = FieldOff.objects.get(area=area)
        except ObjectDoesNotExist:
            fo = FieldOff.objects.get(area='gen')

        tempRoad = Roads(url=url, description=description, locLong=locLong, locLat=locLat, area=area, city=city, state=state, roadType=roadType, GrievType=GrievType, cat=cat, fieldOfficer=fo)
        tempRoad.save()

        tusr = Users.objects.get(id=postedUser)
        rp = PostUsers(roadId=tempRoad, usrId=tusr)
        rp.save()

        data = {
            'status': 200,
            'data': {
                'title': 'successfully registered',
            }
        }
        return HttpResponse(json.dumps(data), content_type="application/json", status=404)


    return HttpResponse("Access Denied", status=403)


def FieldOffForm(request):
    if request.method =='GET':
        name = request.GET['name']
        email = request.GET['email']
        password = request.GET['password']
        area = request.GET['area']
        off = FieldOff(name=name, email=email, password=password, area=area)
        off.save()
        data = {
            'status': 200,
            'data': {
                    'title': 'Offiecer registered',
                    'message': 'Now you can login'
                }
        }
        return HttpResponse(json.dumps(data), content_type="application/json")

def getOfficer(request):
    if request.method =='GET':
        if 'usr_type' in request.GET:
            try:
                temp = FieldOff.objects.get(id=request.GET['id'], officer_type=request.GET['usr_type'])
                data = {
                    'status': 200,
                    'data': {
                            'id': int(temp.id),
                            'name': str(temp.name),
                            'email': str(temp.email),
                            'area': str(temp.area)
                    }
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
            except ObjectDoesNotExist:
                data = {
                    'status': 404,
                    'errors': [
                        {
                            'title': 'User not found',
                            'message': 'Please check user id'
                        }
                    ]
                }
                return HttpResponse(json.dumps(data), content_type="application/json")
    data = {
        'status': 404,
        'errors': [
            {
                'title': 'User not found',
                'message': 'Please check user id'
            }
        ]
    }
    return HttpResponse(json.dumps(data), content_type="application/json")

def Admin(request):
    if 'adminroadg' in request.COOKIES:
        return render(request, 'roadG/dash.html', {})
    return render(request, 'roadG/index.html', {})

def roadPost2(request):
    if request.method == 'GET':
        if 'url' in request.GET:
            url = request.GET['url']
        if 'description' in request.GET:
            description = request.GET['description']
        if 'locLong' in request.GET:
            locLong = request.GET['locLong']
        if 'locLat' in request.GET:
            locLat = request.GET['locLat']
        if 'area' in request.GET:
            area = request.GET['area']
        if 'city' in request.GET:
            city = request.GET['city']
        if 'state' in request.GET:
            state = request.GET['state']
        if 'roadType' in request.GET:
            roadType = request.GET['roadType']
        if 'GrievType' in request.GET:
            GrievType = request.GET['GrievType']
        if 'cat' in request.GET:
            cat = request.GET['cat']
        if 'postedUser' in request.GET:
            postedUser = request.GET['postedUser']

        try:
            fo = FieldOff.objects.get(area=area)
        except ObjectDoesNotExist:
            fo = FieldOff.objects.get(area='gen')

        tempRoad = Roads(url=url, description=description, locLong=locLong, locLat=locLat, area=area, city=city, state=state, roadType=roadType, GrievType=GrievType, cat=cat, fieldOfficer=fo)
        tempRoad.save()

        tusr = Users.objects.get(id=postedUser)
        rp = PostUsers(roadId=tempRoad, usrId=tusr)
        rp.save()

        data = {
            'status': 200,
            'data': {
                'title': 'successfully registered',
            }
        }
        return HttpResponse(json.dumps(data), content_type="application/json")


    return HttpResponse("Access Denied", status=403)

def loginWeb(request):
    if request.method == 'POST':
        temp = LoginWeb(request.POST)
        if temp.is_valid():
            email = temp.cleaned_data['email']
            password = temp.cleaned_data['password']

            try:
                tus = Users(email=email, password=password)
            except ObjectDoesNotExist:
                return HttpResponse("invalid username or password")

            data = {
                'status': 200,
                'data': {
                    'title': 'successfully logged in',
                }
            }
            return HttpResponse(json.dumps(data), content_type="application/json")
        return HttpResponse("Invalid data")
    return HttpResponse("Invalid Method")
