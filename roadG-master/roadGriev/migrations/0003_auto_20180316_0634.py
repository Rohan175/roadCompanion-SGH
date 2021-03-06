# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-03-16 06:34
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0002_auto_20180313_1035'),
    ]

    operations = [
        migrations.CreateModel(
            name='Officers',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('area', models.CharField(max_length=500)),
                ('city', models.CharField(max_length=500)),
                ('state', models.CharField(max_length=500)),
            ],
        ),
        migrations.AddField(
            model_name='users',
            name='user_type',
            field=models.CharField(default='user', max_length=2000),
        ),
        migrations.AddField(
            model_name='officers',
            name='officer_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='roadGriev.Users'),
        ),
    ]
