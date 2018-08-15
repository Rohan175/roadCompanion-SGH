import urllib
import cv2
import numpy as np
import matplotlib.pyplot as plt


def check(img2, answer):
    answer = 0
    #     img2=cv2.imread("opencv/temp.jpg")

    height, width = img2.shape[:2]
    m_height, m_width = 400, 400
    if (height < m_height and width < m_width):
        #cv2.imshow("original", img2)
        pass
    else:
        if height > m_height or width > m_width:
            sf = m_height / float(height)
        if m_width / float(width) < sf:
            sf = m_width / float(width)
        img1 = cv2.resize(img2, None, fx=sf, fy=sf, interpolation=cv2.INTER_CUBIC)

    #     cv2.imshow("croped",img2)
    #     cv2.waitKey(0)
    #     cv2.destroyAllWindows()

    img = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
    #     plt.hist(img.ravel(),256,[0,256])
    #     plt.show()

    imgrow, imgcol = img.shape;
    count = 0
    black = 0
    for i in range(0, imgrow):
        for j in range(0, imgcol):
            if img[i][j] > 80 and img[i][j] < 200:
                count = count + 1;
            if img[i][j] < 20:
                black = black + 1;
    if count > (imgrow * imgcol) / 3 and black < (imgrow * imgcol) / 2:
        answer = 1
    else:
        answer = 0
    #     print(answer)
    return answer


def opencv(img1):
    #     urllib.request.urlretrieve(url, "opencv/temp.jpg")
    #     img1=cv2.imread("opencv/temp.jpg")

    height, width = img1.shape[:2]
    m_height, m_width = 400, 400
    if (height < m_height and width < m_width):
        #cv2.imshow("original", img1)
        pass
    else:
        if height > m_height or width > m_width:
            sf = m_height / float(height)
        if m_width / float(width) < sf:
            sf = m_width / float(width)
        img1 = cv2.resize(img1, None, fx=sf, fy=sf, interpolation=cv2.INTER_CUBIC)

    #     cv2.imshow("croped",img1)
    #     cv2.waitKey(0)
    #     cv2.destroyAllWindows()

    gray = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)
    #     ans = check(img1,answer)
    # answer=0

    #     print(ans)

    #     if ans == 1:

    # blur = cv2.GaussianBlur(gray,(5,5),0)
    ret, thresh = cv2.threshold(gray, 129, 255, cv2.THRESH_BINARY)
    edges = cv2.Canny(thresh, 100, 400)
    kernel = np.ones((5, 5), np.uint8)
    dilation = cv2.dilate(edges, kernel, iterations=3)
    image, contours, hierarchy = cv2.findContours(dilation, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    size = img1.shape[:2]
    # print(contours.shape)
    matrix_contour = []
    list_of_image = []
    for contour in contours:

        x, y, w, h = cv2.boundingRect(contour)
        if h > (size[0] - 2) and w > (size[1] - 2) or h < (size[0] / 13) and w < (size[1] / 13):
            continue
        # img=cv2.drawContours(img1,contour,-1,(0,255,0),5)
        # img2=cv2.rectangle(img1,(x,y),(x+w,y+h),(0,255,0),2)#convex hull rectangle
        # cv2.imshow("x",img2)
        matrix_contour.append([x, y, w, h])
        list_of_image.append(img1[y:y + h, x:x + w])

    for i in range(0, len(list_of_image)):
        #     cv2.imshow("a"+chr(i),list_of_image[i])
        list_of_image[i] = cv2.cvtColor(list_of_image[i], cv2.COLOR_BGR2GRAY)
    #     plt.hist(list_of_image[i].ravel(),256,[0,256])
    #     plt.show()

    for li in range(0, len(list_of_image)):
        imgrow, imgcol = list_of_image[li].shape;
        #         print(imgrow,imgcol)
        count = 0
        for i in range(0, imgrow):
            for j in range(0, imgcol):
                if list_of_image[li][i][j] < 100:
                    count = count + 1;
        if count > (imgrow * imgcol / 2):
            return 1
            break
        #             img2=cv2.rectangle(img1,(matrix_contour[li][0],matrix_contour[li][1]),(matrix_contour[li][0]+matrix_contour[li][2],matrix_contour[li][1]+matrix_contour[li][3]),(0,255,0),2)
        #             print("found")
        else:
            return 0


#             print("not found")
#         print(matrix_contour)
#         cv2.imshow("img",img2)

#     cv2.waitKey(0)
#     cv2.destroyAllWindows
# else:
#     print("road not found")

def pa(pp):
    pp =5
    answer = 0
    # url = input('enter your url')
    # urllib.request.urlretrieve(url, "pothhole/temp.jpg")
    # img1 = cv2.imread("pothhole/temp.jpg")
    img1 = cv2.imread("pdpu_image/try/curr.jpg")

    ans = check(img1, answer)
    if ans == 1:
        an = opencv(img1)

        if an == 1:
            return 1
        else:
            return 0

    else:
        return 0


def ap(url):
    pal = 0
    # url = input('enter your url')
    urllib.request.urlretrieve(url, "pdpu_image/try/curr.jpg")
    image1 = cv2.resize(cv2.imread("pdpu_image/try/curr.jpg"), (224, 224)).astype(np.float32)
    # image1 = cv2.resize(cv2.imread("pdpu_image/try/curr.jpg"), (224, 224)).astype(np.float32)

    for p in range(263):
        if p>=1:
            a = "pothhole/" + str(p) + ".jpg"
            # print(a)


            image2 = cv2.resize(cv2.imread(a), (224, 224)).astype(np.float32)

            difference = cv2.subtract(image1, image2)

            result = not np.any(difference)

            if result is True:
                print("same pot-hole")
                break

            else:
                # cv2.imwrite("apap.jpg", difference)
                if p == 263:
                    print("condition false")

            if p<=150:
                b = "treefall/" + str(p) + ".jpg"
                # print(b)

                image3 = cv2.resize(cv2.imread(b), (224, 224)).astype(np.float32)
                difference2 = cv2.subtract(image1, image3)

                result2 = not np.any(difference2)

                if result2 is True:
                    print("same tree fall")
                    break

            else:
                if p==150:
                    print('no tree fall')



    if pal == 0:
        pp = 5
        lap = pa(pp)
        # print(lap)
        return lap
