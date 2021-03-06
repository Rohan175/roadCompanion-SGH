# -*- coding: utf-8 -*-
# Generated by Django 1.11.7 on 2018-03-08 03:17
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('roadG', '0004_auto_20180308_0158'),
    ]

    operations = [
        migrations.CreateModel(
            name='FieldOff',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=200)),
                ('email', models.CharField(max_length=1000)),
                ('password', models.CharField(max_length=1000)),
                ('time', models.DateTimeField(auto_now_add=True)),
            ],
        ),
        migrations.CreateModel(
            name='OffRoads',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('roadName', models.CharField(max_length=1000)),
                ('districtName', models.CharField(max_length=1000)),
                ('fieldOfficer', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='roadG.FieldOff')),
            ],
        ),
        migrations.AddField(
            model_name='roads',
            name='votes',
            field=models.IntegerField(default=0),
        ),
    ]
