# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-24 13:49
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0021_users_address'),
    ]

    operations = [
        migrations.CreateModel(
            name='TypesOfGrievancesModel',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=2500)),
            ],
        ),
    ]
