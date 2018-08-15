# -*- coding: utf-8 -*-
# Generated by Django 1.11.7 on 2018-03-11 04:30
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('roadG', '0013_auto_20180311_0957'),
    ]

    operations = [
        migrations.DeleteModel(
            name='Admins',
        ),
        migrations.DeleteModel(
            name='ExecutiveOff',
        ),
        migrations.RemoveField(
            model_name='users',
            name='usr_type',
        ),
        migrations.AddField(
            model_name='fieldoff',
            name='officer_type',
            field=models.CharField(default='field officer', max_length=100),
            preserve_default=False,
        ),
    ]
