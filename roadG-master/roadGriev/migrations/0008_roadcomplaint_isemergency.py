# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-16 18:16
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0007_roadcomplaint_workstatus'),
    ]

    operations = [
        migrations.AddField(
            model_name='roadcomplaint',
            name='isEmergency',
            field=models.IntegerField(default=0),
            preserve_default=False,
        ),
    ]
