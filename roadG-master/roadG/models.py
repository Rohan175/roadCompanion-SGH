from django.db  import models


class Users(models.Model):
    name = models.CharField(max_length=200)
    email = models.CharField(max_length=1000)
    password = models.CharField(max_length=1000)
    time = models.DateTimeField(auto_now=False, auto_now_add=True)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

class Roads(models.Model):
    url = models.CharField(max_length=2000)
    description = models.CharField(max_length=2000)
    locLong = models.CharField(max_length=100)
    locLat = models.CharField(max_length=100)
    votes = models.IntegerField(default=0)
    area = models.CharField(max_length=100, default="0")
    city = models.CharField(max_length=100, default="0")
    state = models.CharField(max_length=100, default="0")
    roadType = models.CharField(max_length=100, default="")
    GrievType = models.CharField(max_length=100)
    cat = models.CharField(max_length=100)
    workStatus = models.CharField(max_length=100, default='not started')
    hirWeight = models.IntegerField(default=0)
    fieldOfficer = models.ForeignKey('FieldOff', on_delete=models.CASCADE)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)

class PostUsers(models.Model):
    roadId = models.ForeignKey('Roads', on_delete=models.CASCADE)
    usrId = models.ForeignKey('Users', on_delete=models.CASCADE)


class FieldOff(models.Model):
    name = models.CharField(max_length=200)
    email = models.CharField(max_length=1000)
    password = models.CharField(max_length=1000)
    area = models.CharField(max_length=100)
    officer_type = models.CharField(max_length=100)
    time = models.DateTimeField(auto_now=False, auto_now_add=True)

    def __str__(self):
        return str(self.id)
    def __unicode__(self):
        return str(self.id)
