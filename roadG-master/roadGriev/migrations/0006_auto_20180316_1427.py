# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-16 14:27
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0005_auto_20180316_1426'),
    ]

    operations = [
        migrations.AlterField(
            model_name='roadcomplaint',
            name='time',
            field=models.DateTimeField(auto_now_add=True),
        ),
    ]