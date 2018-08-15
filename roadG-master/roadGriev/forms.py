from django import forms

class RegisterForm(forms.Form):
    name = forms.CharField()
    email = forms.CharField()
    password = forms.CharField()

class LoginForm(forms.Form):
    email = forms.CharField()
    password = forms.CharField()

class ComplaintFomr(forms.Form):
    location_latitude = forms.CharField()
    location_longitude = forms.CharField()
    description = forms.CharField()
    road_type = forms.CharField()
    grievence_type = forms.CharField()
    area = forms.CharField()
    city = forms.CharField()
    state = forms.CharField()
    isEmergency = forms.CharField()
    postedUserID = forms.CharField()

class addOfficerForm(forms.Form):
    name = forms.CharField()
    email = forms.CharField()
    password = forms.CharField()
    taluka = forms.CharField()
    city = forms.CharField()
    state = forms.CharField()
