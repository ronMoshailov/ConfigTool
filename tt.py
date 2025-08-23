import cv2
import numpy as np

# קריאת התמונה
img = cv2.imread("01.25.png")

# יצירת מסנן חידוד
kernel = np.array([[0, -1, 0],
                   [-1, 5,-1],
                   [0, -1, 0]])

# החלת הפילטר
sharpened = cv2.filter2D(img, -1, kernel)

# שמירת התוצאה
cv2.imwrite("output_sharpened.png", sharpened)
