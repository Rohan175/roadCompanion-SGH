"""roadGriev URL Configuration

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
from django.conf.urls import url, include
from django.contrib import admin
from  . import views
from . import settings
from django.views.decorators.csrf import csrf_exempt
from django.conf.urls.static import static

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    #web portal
    url(r'^$', csrf_exempt(views.Index)),
    url(r'^logout/', csrf_exempt(views.logout)),
    url(r'^requests/', csrf_exempt(views.incRequests)),
    url(r'^reports/', csrf_exempt(views.incReports)),
    url(r'^Complaint/(?P<complaint_id>[0-9]+)$', csrf_exempt(views.complaintFull)),
    url(r'^addOfficer/', csrf_exempt(views.addOfficer)),
    url(r'^setHirarchy/', csrf_exempt(views.setHirarchy)),
    url(r'^applyHierarchy/$', csrf_exempt(views.applyHierarchy)),
    url(r'^Test$', csrf_exempt(views.Test)),
    url(r'^AddCatagory$', csrf_exempt(views.Catagory)),
    url(r'^AddCatagoryForm$', csrf_exempt(views.AddCatagory)),
    url(r'^DeleteCatagory$', csrf_exempt(views.deleteCatagory)),

    #api
    url(r'^Login$', csrf_exempt(views.Login)),
    url(r'^Signup$', csrf_exempt(views.Signup)),
    url(r'^OfficerSignup$', csrf_exempt(views.OfficerSignup)),
    url(r'^UserComplaints$', csrf_exempt(views.UserComplaints)),
    url(r'^Complaint$', csrf_exempt(views.complaint)),
    url(r'^ComplaintStatusChange$', csrf_exempt(views.ComplaintStatusChange)),
    url(r'^EmergencyStatusChanged/$', csrf_exempt(views.EmergencyStatusChanged)),
    url(r'^getHierarchy$', csrf_exempt(views.getHierarchy)),
    url(r'^SortedData$', csrf_exempt(views.SortedData)),
    url(r'^setEstimatedTime$', csrf_exempt(views.setEstimatedTime)),
    url(r'^TypesOfGrievances$', csrf_exempt(views.TypesOfGrievances)),
    url(r'^RequestsSort$', csrf_exempt(views.RequestsSort)),
    url(r'^AllComplaints$', csrf_exempt(views.AllComplaints)),
    url(r'^ArchiveData$', csrf_exempt(views.ArchiveData)),
    url(r'^comment', csrf_exempt(views.comment)),

    url(r'^getSrOfficerList/$', csrf_exempt(views.getSrOfficerList)),
    url(r'^getRoadsList/$', csrf_exempt(views.getRoadsList))
]

urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)
urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
