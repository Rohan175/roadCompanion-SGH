from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib

host = 'smtp.gmail.com'
port = 587
username = "ankitmodi31198@gmail.com"
password = "maanu10-12-1998"
from_list = username
to_list = ["rohananna98@gmail.com"]
try:
    email_con = smtplib.SMTP(host, port)
    print(email_con.ehlo())
    print(email_con.starttls())
    print(email_con.login(username, password))

    msg = MIMEMultipart("alternative")
    msg['subject'] = "Road grievence checking"
    msg["From"] = from_list
    msg["To"] = to_list[0]
    plain_txt = "Hello sir,"
    html_txt = """
    <html>
        <head>
            <title>Auto Generated Mail to field officer</title>
        </head>
        <body>
            <div>
                <div>Hello 'Babubhai',</div>
                <div>

                    <pre style="font-size: 15px;">	You have an emergency complain of 'Pot-hole' at 'Kh-road, Sargasan, Gandhinagar' filed on '24-Mar-18 21:43 Hrs'.</pre>

                </div>
                <div>
                    <div>
                        view complaint
                    </div>
                    <div>
                        <a href="#">http://tvtropes.org/pmwiki/discussion.php?id=9e8idyy8z2snc5odimcro2xn</a>
                    </div>
                </div>
                <br>
                <div>Thank You.</div>

            </div>
        </body>
    </html>"""

    part_1 = MIMEText(plain_txt, 'plain')
    part_2 = MIMEText(html_txt, 'html')

    msg.attach(part_1)
    msg.attach(part_2)

    # print(msg.as_string())

    print(email_con.sendmail(from_list, to_list, msg.as_string()))
    print(email_con.quit())

except smtplib.SMTPAuthenticationError:
    print("Error loading message")

except smtplib.SMTPException:
    print("Error loading message")
