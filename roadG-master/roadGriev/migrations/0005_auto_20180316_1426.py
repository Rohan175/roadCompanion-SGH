# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-16 14:26
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0004_officersconnections_roadcomplaint'),
    ]

    operations = [
        migrations.CreateModel(
            name='RoadComplaintPostedUser',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
            ],
        ),
        migrations.AddField(
            model_name='roadcomplaint',
            name='time',
            field=models.DateTimeField(auto_now_add=True, null=True),
        ),
        migrations.AddField(
            model_name='roadcomplaintposteduser',
            name='complaint_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='roadGriev.RoadComplaint'),
        ),
        migrations.AddField(
            model_name='roadcomplaintposteduser',
            name='user_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='roadGriev.Users'),
        ),
    ]
