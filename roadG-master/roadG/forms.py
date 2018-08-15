from django import forms

class Register(forms.Form):
    name = forms.CharField(max_length=200)
    email = forms.CharField(max_length=1000)
    password = forms.CharField(max_length=1000)

class Road(forms.Form):
    url = forms.CharField(max_length=2000)
    name = forms.CharField(max_length=100)
    roadType = forms.CharField(max_length=100)
    description = forms.CharField(max_length=2000)
    locLong = forms.CharField(max_length=100)
    locLat = forms.CharField(max_length=100)
    postedUser = forms.IntegerField()
    fieldOfficer = forms.CharField(max_length=100)

class Signup(forms.Form):
    email = forms.CharField(max_length=1000)
    password = forms.CharField(max_length=1000)

class LoginWeb(forms.Form):
    email = forms.CharField(max_length=1000)
    password = forms.CharField(max_length=1000)
