    try:
        sameComplaints = list(RoadComplaint.objects.filter(road_type=road_type, grievence_type=grievence_type, area=area, city=city, state=state))
    #if not found then save new one
    except ObjectDoesNotExist:
        try:
            tempUsr = Users.objects.get(id=postedUserID)
        except ObjectDoesNotExist:
            data = {
                'status': 1,
                'error' : {
                    'messages': "User not found"
                }
            }
            return JsonResponse(data)
        newComplaint = RoadComplaint(url=url, location_latitude=location_latitude, location_longitude=location_longitude, description=description, road_type=road_type, grievence_type=grievence_type, area=area, city=city, state=state, isEmergency=isEmergency, current_officer=curr.officer_id)
        newComplaint.save()
        tempUsrToCom = RoadComplaintPostedUser(user_id=tempUsr, complaint_id=newComplaint)
        tempUsrToCom.save()
        try:
            tempOff = Officers.objects.get(officer_id=operator)
        except ObjectDoesNotExist:
            tempOff = Officers.objects.get(area='general', city=city, state=state)
        except Exception as e:
            return HttpResponse(e)  ##field Officer not found
        tempComToOff = RoadComplaintToOfficer(complaint_id=newComplaint, officer_id=tempOff.officer_id)
        tempComToOff.save()
        data = {
            'status': 0,
            'data' : {
                'id': str(newComplaint.id)
            }
        }
        return JsonResponse(data)
    ##nearby complaints found
    allSameComplaint = []
    if len(sameComplaints) > 0:
        for com in sameComplaints:
            print("jkdhfjsdk")
            tempDis = Distance(float(location_latitude), float(location_longitude), float(com.location_latitude), float(com.location_longitude))
            print(tempDis)
            if tempDis <= MIN_DISTANCE:
                allSameComplaint.append( [com, tempDis] )
        sortedComplaints = sorted(allSameComplaint, key=lambda x: x[1], reverse=False)

        try:
            sameRoad = sortedComplaints[0][0]
        except Exception as e:
            try:
                tempUsr = Users.objects.get(id=postedUserID)
            except ObjectDoesNotExist:
                data = {
                    'status': 1,
                    'error' : {
                        'messages': "User not found"
                    }
                }
                return JsonResponse(data)
            newComplaint = RoadComplaint(url=url, location_latitude=location_latitude, location_longitude=location_longitude, description=description, road_type=road_type, grievence_type=grievence_type, area=area, city=city, state=state, isEmergency=isEmergency, current_officer=curr.officer_id)
            newComplaint.save()
            tempUsrToCom = RoadComplaintPostedUser(user_id=tempUsr, complaint_id=newComplaint)
            tempUsrToCom.save()
            try:
                tempOff = Officers.objects.get(officer_id=operator)
            except ObjectDoesNotExist:
                tempOff = Officers.objects.get(area='general', city=city, state=state)
            except Exception as e:
                return HttpResponse(e)  ##field Officer not found
            tempComToOff = RoadComplaintToOfficer(complaint_id=newComplaint, officer_id=tempOff.officer_id)
            tempComToOff.save()
            data = {
                'status': 0,
                'data' : {
                    'id': str(newComplaint.id)
                }
            }
            return JsonResponse(data)
        try:
            tempUsr = Users.objects.get(id=postedUserID)
        except ObjectDoesNotExist:
            data = {
                'status': 1,
                'error' : {
                    'messages': "User not found"
                }
            }
            return JsonResponse(data)
        tempUsrToCom = RoadComplaintPostedUser(user_id=tempUsr, complaint_id=sameRoad)
        tempUsrToCom.save()
        data = {
            'status': 0,
            'data' : {
                'id': str(sameRoad.id)
            }
        }
        return JsonResponse(data)
    try:
        tempUsr = Users.objects.get(id=postedUserID)
    except ObjectDoesNotExist:
        data = {
            'status': 1,
            'error' : {
                'messages': "User not found"
            }
        }
        return JsonResponse(data)
    newComplaint = RoadComplaint(url=url, location_latitude=location_latitude, location_longitude=location_longitude, description=description, road_type=road_type, grievence_type=grievence_type, area=area, city=city, state=state, isEmergency=isEmergency, current_officer=curr.officer_id)
    newComplaint.save()
    tempUsrToCom = RoadComplaintPostedUser(user_id=tempUsr, complaint_id=newComplaint)
    tempUsrToCom.save()
    try:
        tempOff = Officers.objects.get(officer_id=operator)
    except ObjectDoesNotExist:
        tempOff = Officers.objects.get(area='general', city=city, state=state)
    except Exception as e:
        return HttpResponse(e)  ##field Officer not found
    tempComToOff = RoadComplaintToOfficer(complaint_id=newComplaint, officer_id=tempOff.officer_id)
    tempComToOff.save()
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
return JsonResponse(data)temp.py
