from django.db import models


#stores all Users personal data
class Users(models.Model):
    name = models.CharField(max_length=2000)
    email = models.CharField(max_length=2000)
    password = models.CharField(max_length=2000)
    address = models.CharField(max_length=2000, null=True, blank=True)
    user_type = models.CharField(max_length=2000, default="user")
    time = models.DateTimeField(auto_now=False, auto_now_add=True)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

#stores only Officers related data
class Officers(models.Model):
    officer_id = models.ForeignKey('Users', on_delete=models.CASCADE)
    area = models.CharField(max_length=500, null=True, blank=True)
    city = models.CharField(max_length=500, null=True, blank=True)
    state = models.CharField(max_length=500, null=True, blank=True)
    road_code = models.ForeignKey('RoadMapping', on_delete=models.CASCADE, null=True, blank=True)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

#connecct junior Officers to senior Officers
class OfficersConnections(models.Model):
    jr_officer_id = models.ForeignKey('Users', related_name='JuniorOfficer', on_delete=models.CASCADE)
    sr_officer_id = models.ForeignKey('Users', related_name='SeniorOfficer', on_delete=models.CASCADE)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

#single road complaint
class RoadComplaint(models.Model):
    url = models.URLField(max_length=2000)
    location_latitude = models.DecimalField(max_digits=9, decimal_places=6)
    location_longitude = models.DecimalField(max_digits=9, decimal_places=6)
    description = models.CharField(max_length=2500)
    road_type = models.CharField(max_length=500)#nh, sh, district
    grievence_type = models.CharField(max_length=500)
    area = models.CharField(max_length=500)
    city = models.CharField(max_length=500)
    state = models.CharField(max_length=500)
    isEmergency = models.IntegerField(default=0, null=True)
    workStatus = models.CharField(max_length=500, default="pending")
    priority = models.IntegerField(default=1, null=True)
    current_officer = models.ForeignKey('Users', on_delete=models.CASCADE)
    time = models.DateTimeField(auto_now=False, auto_now_add=True, null=False)
    comments = models.CharField(max_length=2500, null=True)
    esitmated_time = models.DateField(auto_now=False, auto_now_add=False, null=True)


    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

class RoadComplaintPostedUser(models.Model):
    user_id = models.ForeignKey('Users', on_delete=models.CASCADE)
    complaint_id = models.ForeignKey('RoadComplaint', on_delete=models.CASCADE)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

class RoadComplaintToOfficer(models.Model):
    complaint_id = models.ForeignKey('RoadComplaint', on_delete=models.CASCADE)
    officer_id = models.ForeignKey('Users', on_delete=models.CASCADE)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

class RoleTable(models.Model):
    role_name = models.CharField(max_length=2500)
    weight = models.IntegerField()
    idName = models.CharField(max_length=500, default="")
    region = models.CharField(max_length=500, default="")

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)


class TypesOfGrievancesModel(models.Model):
    title = models.CharField(max_length=2500)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)


class ArchivedRoadComplaint(models.Model):
    url = models.URLField(max_length=2000)
    location_latitude = models.DecimalField(max_digits=9, decimal_places=6)
    location_longitude = models.DecimalField(max_digits=9, decimal_places=6)
    description = models.CharField(max_length=2500)
    road_type = models.CharField(max_length=500)#nh, sh, district
    grievence_type = models.CharField(max_length=500)
    area = models.CharField(max_length=500)
    city = models.CharField(max_length=500)
    state = models.CharField(max_length=500)
    isEmergency = models.IntegerField()
    workStatus = models.CharField(max_length=500, default="pending")
    current_officer = models.ForeignKey('Users', on_delete=models.CASCADE)
    time = models.DateTimeField(auto_now=False, auto_now_add=True, null=False)
    comments = models.CharField(max_length=2500, null=True)
    esitmated_time = models.DateField(auto_now=False, auto_now_add=False, null=True)
    isArchived = models.IntegerField(null=True)


class RoadMapping(models.Model):
    road_code = models.CharField(max_length=50)
    road_name = models.CharField(max_length=2000, null=True, blank=True)

    def __str__(self):
        return str(self.road_code)
    def __unicode__(self):
        return str(self.id)




#
# class Hierarchy(models.Model):
