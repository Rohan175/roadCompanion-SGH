# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-20 23:04
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0015_roletable'),
    ]

    operations = [
        migrations.AddField(
            model_name='roletable',
            name='idName',
            field=models.CharField(default='', max_length=500),
        ),
    ]
