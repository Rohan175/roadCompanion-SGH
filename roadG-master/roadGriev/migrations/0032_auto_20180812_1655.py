# Generated by Django 2.0.7 on 2018-08-12 16:55

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('roadGriev', '0031_auto_20180812_1642'),
    ]

    operations = [
        migrations.AlterField(
            model_name='officers',
            name='road_code',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='roadGriev.RoadMapping'),
        ),
    ]
