# -*- coding: utf-8 -*-
# Generated by Django 1.11.7 on 2018-03-11 00:37
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('roadG', '0009_auto_20180311_0138'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='offroads',
            name='fieldOfficer',
        ),
        migrations.DeleteModel(
            name='OffRoads',
        ),
    ]
