from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib

def mail(mail_to, subject_mail, content_mail):
    host = 'smtp.gmail.com'
    port = 587
    username = "rnbdepartment@gmail.com"
    password = "rnb@gujarat"
    from_list = username
    to_list = mail_to
    try:
        email_con = smtplib.SMTP(host, port)

        email_con.ehlo()
        email_con.starttls()
        email_con.login(username, password)

        msg = MIMEMultipart("alternative")
        msg['subject'] = subject_mail
        msg["From"] = from_list
        msg["To"] = to_list[0:]
        html_txt = content_mail

        part = MIMEText(html_txt, 'html')

        msg.attach(part)

        # print(msg.as_string())

        email_con.sendmail(from_list, to_list, msg.as_string())
        email_con.quit()

    except smtplib.SMTPAuthenticationError:
        print("Error loading message")

    except smtplib.SMTPException:
        print("Error loading message")
