"""roadG URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin
from . import views
from django.views.decorators.csrf import csrf_exempt

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^$', csrf_exempt(views.index)),
    url(r'^user/$', csrf_exempt(views.user)),
    url(r'^user/search/$', csrf_exempt(views.searchUser)),
    url(r'^user/update/$', csrf_exempt(views.updateUser)),
    url(r'^road/$', csrf_exempt(views.road)),
    url(r'^road/search/$', csrf_exempt(views.roadSearch)),
    url(r'^road/update/$', csrf_exempt(views.updateRoad)),
    url(r'^road/upVote/$', csrf_exempt(views.upVote)),
    url(r'^road/downVote/$', csrf_exempt(views.downVote)),
    url(r'^officer/$', csrf_exempt(views.officer)),
    url(r'^roadAdmins/$', csrf_exempt(views.roadAdmins)),
    url(r'^login/$', csrf_exempt(views.login)),
    url(r'^Signup/$', csrf_exempt(views.Signup)),
    url(r'^roadPost/$', csrf_exempt(views.roadPost)),
    url(r'^roadPost2/$', csrf_exempt(views.roadPost2)),
    url(r'^getOfficer/$', csrf_exempt(views.getOfficer)),
    url(r'^Admin/$', csrf_exempt(views.Admin)),
    url(r'^loginWeb/$', csrf_exempt(views.loginWeb))
]
