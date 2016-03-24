package com.yaowang.util.img.zxing;

import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class BuildDimensionCode {
//	public static void main(String[] args) {
//		// 生成
//		try {
//			String content = "http://www.baidu.com";
//			String path = "C:/Users/Administrator/Desktop/";
//
//			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//
//			Map hints = new HashMap();
//			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//			BitMatrix bitMatrix = multiFormatWriter.encode(content,
//					BarcodeFormat.QR_CODE, 400, 400, hints);
//			File file1 = new File(path, "test.jpg");
//			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 解析
//		try {
//			MultiFormatReader formatReader = new MultiFormatReader();
//			String filePath = "C:/Users/Administrator/Desktop/test.jpg";
//			File file = new File(filePath);
//			BufferedImage image = ImageIO.read(file);
//			;
//			LuminanceSource source = new BufferedImageLuminanceSource(image);
//			Binarizer binarizer = new HybridBinarizer(source);
//			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
//			Map hints = new HashMap();
//			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//			Result result = formatReader.decode(binaryBitmap, hints);
//
//			System.out.println("result = " + result.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * 生成二维码
	 * @param content
	 * @return
	 * @throws WriterException
	 */
	public static BitMatrix buildImg(String content) throws WriterException {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
		return bitMatrix;
	}
}
