# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-16 18:17
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0008_roadcomplaint_isemergency'),
    ]

    operations = [
        migrations.AlterField(
            model_name='roadcomplaint',
            name='isEmergency',
            field=models.IntegerField(max_length=1),
        ),
    ]
