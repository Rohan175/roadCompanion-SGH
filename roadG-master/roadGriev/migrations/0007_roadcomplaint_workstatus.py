# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-16 17:53
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0006_auto_20180316_1427'),
    ]

    operations = [
        migrations.AddField(
            model_name='roadcomplaint',
            name='workStatus',
            field=models.CharField(default='pending', max_length=500),
        ),
    ]