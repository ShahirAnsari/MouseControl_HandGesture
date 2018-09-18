/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouse;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvOr;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_ANY;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvCreateCameraCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvQueryFrame;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSetCaptureProperty;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;
import java.awt.AWTException;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_WIDTH;

/**
 *
 * @author Walatima
 */
public class Mouse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException {
        // TODO code application logic here
        IplImage img1,imgbinG,imgbinB;
        IplImage imghsv;
        CvScalar Bminc = cvScalar(95,150,75,0);
        CvScalar Bmaxc = cvScalar(145,255,255,0);
        CvScalar Gminc = cvScalar(40,50,60,0);
        CvScalar Gmaxc = cvScalar(80,255,255,0);
        
        CvArr mask;
        
        
        int w=341,h=240;
        
        imghsv = cvCreateImage(cvSize(w,h),8,3);
        imgbinG = cvCreateImage(cvSize(w,h),8,1);
        imgbinB = cvCreateImage(cvSize(w,h),8,1);
        IplImage imgC = cvCreateImage(cvSize(w,h),8,1);
        
        CvSeq contour1 = new CvSeq();
        CvSeq contour2 = null;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));
        
        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_WIDTH,w);
        cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_HEIGHT,w);
       
        
        while(true)
        { 
            img1 = cvQueryFrame(capture1);
            if(img1 == null){
                System.out.println("NO Image Detected");
                break;
            }
            
            imgbinB = filterstuff.Filter(img1, imghsv, imgbinB, Bmaxc, Bminc, contour1, contour2, storage, moments, 1, 0);
            imgbinG = filterstuff.Filter(img1, imghsv, imgbinG, Gmaxc, Gminc, contour1, contour2, storage, moments, 0, 1);
            
            cvOr(imgbinB,imgbinG,imgC,mask=null);
            
            cvShowImage("COMBINED",imgC);
            cvShowImage("Original",img1);
            char c = (char)cvWaitKey(15);
            if(c == 'q')
                break;
        }
        
        
        
        
    }
    
}
