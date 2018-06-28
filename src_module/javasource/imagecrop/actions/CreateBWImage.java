// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package imagecrop.actions;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;

/**
 * 
 */
public class CreateBWImage extends CustomJavaAction<Boolean>
{
	private IMendixObject ColorImage;
	private IMendixObject BWImage;
	private Long thumbnailWidth;
	private Long thumbnailHeight;

	public CreateBWImage(IContext context, IMendixObject ColorImage, IMendixObject BWImage, Long thumbnailWidth, Long thumbnailHeight)
	{
		super(context);
		this.ColorImage = ColorImage;
		this.BWImage = BWImage;
		this.thumbnailWidth = thumbnailWidth;
		this.thumbnailHeight = thumbnailHeight;
	}

	@Override
	public Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		InputStream is = null;
		InputStream stream = null;
		try {
			is = Core.getImage(getContext(), this.ColorImage, false);
			BufferedImage original = ImageIO.read(is);
//			BufferedImage binarized = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

////			int red;
//			int newPixel;
//			int threshold = 230;

//			for (int i = 0; i < original.getWidth(); i++) {
//				for (int j = 0; j < original.getHeight(); j++) {
//
//					// Get pixels
//					red = new Color(original.getRGB(i, j)).getRed();
//
//					int alpha = new Color(original.getRGB(i, j)).getAlpha();
//
//					if (red > threshold) {
//						newPixel = 0;
//					}
//					else {
//						newPixel = 255;
//					}
//					newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
//					binarized.setRGB(i, j, newPixel);
//				}
//			}
			
//            for (int x = 0; x < original.getWidth(); x++) {
//                for (int y = 0; y < original.getHeight(); y++) {
//                    Color color = new Color(original.getRGB(x, y));
//                    int red = color.getRed();
//                    int green = color.getGreen();
//                    int blue = color.getBlue();
//
//                    red = green = blue = (int)(red * 0.299 + green * 0.587 + blue * 0.114);
//                    color = new Color(red, green, blue);
//                    int rgb = color.getRGB();
//                    original.setRGB(x, y, rgb);
//                }
//            }
			
            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            op.filter(original, original);

			String formatName = ScaleImage.getFormatName( Core.getImage(getContext(), this.ColorImage, false) );
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(original, formatName, os);
			stream = new ByteArrayInputStream(os.toByteArray());
			Core.storeImageDocumentContent(getContext(), this.BWImage, stream, 
					this.thumbnailWidth.intValue(), this.thumbnailHeight.intValue());
		}
		catch (IOException e) {
			Core.getLogger(this.toString()).error(e);
		} finally {
			if (is != null)
				is.close();
			if (stream != null)
				stream.close();
		}

		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public String toString()
	{
		return "CreateBWImage";
	}

	// BEGIN EXTRA CODE
//	private static int colorToRGB(int alpha, int red, int green, int blue) {
//		int newPixel = 0;
//		newPixel += alpha;
//		newPixel = newPixel << 8;
//		newPixel += red;
//		newPixel = newPixel << 8;
//		newPixel += green;
//		newPixel = newPixel << 8;
//		newPixel += blue;
//
//		return newPixel;
//	}
	// END EXTRA CODE
}
