# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-25 05:08
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0023_roadcomplaint_comments'),
    ]

    operations = [
        migrations.CreateModel(
            name='ArchivedRoadComplaint',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('url', models.URLField(max_length=2000)),
                ('location_latitude', models.DecimalField(decimal_places=6, max_digits=9)),
                ('location_longitude', models.DecimalField(decimal_places=6, max_digits=9)),
                ('description', models.CharField(max_length=2500)),
                ('road_type', models.CharField(max_length=500)),
                ('grievence_type', models.CharField(max_length=500)),
                ('area', models.CharField(max_length=500)),
                ('city', models.CharField(max_length=500)),
                ('state', models.CharField(max_length=500)),
                ('isEmergency', models.IntegerField()),
                ('workStatus', models.CharField(default='pending', max_length=500)),
                ('time', models.DateTimeField(auto_now_add=True)),
                ('comments', models.CharField(max_length=2500, null=True)),
                ('esitmated_time', models.DateField(null=True)),
                ('current_officer', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='roadGriev.Users')),
            ],
        ),
    ]