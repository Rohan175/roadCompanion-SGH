from django.contrib import admin
from .models import *

admin.site.register(Users)
admin.site.register(Officers)
admin.site.register(OfficersConnections)
admin.site.register(RoadComplaint)
admin.site.register(RoadComplaintPostedUser)
admin.site.register(RoadComplaintToOfficer)
admin.site.register(RoleTable)
admin.site.register(TypesOfGrievancesModel)
admin.site.register(ArchivedRoadComplaint)
admin.site.register(RoadMapping)
