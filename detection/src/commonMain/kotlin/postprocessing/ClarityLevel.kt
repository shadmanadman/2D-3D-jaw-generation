//package postprocessing
//
//
//object ClarityLevel {
//
//    private fun convertToGrayscale(bitmap: Bitmap): Bitmap {
//        val grayscaleBitmap =
//            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(grayscaleBitmap)
//        val paint = Paint()
//        val colorMatrix = ColorMatrix()
//        colorMatrix.setSaturation(0f)
//        val filter = ColorMatrixColorFilter(colorMatrix)
//        paint.colorFilter = filter
//        canvas.drawBitmap(bitmap, 0f, 0f, paint)
//        return grayscaleBitmap
//    }
//
//
//    private fun applySobelFilter(bitmap: Bitmap): Bitmap {
//        val width = bitmap.width
//        val height = bitmap.height
//        val sobelBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//
//        val sobelX = arrayOf(
//            intArrayOf(-1, 0, 1),
//            intArrayOf(-2, 0, 0),
//            intArrayOf(1, 2, 1)
//        )
//        val sobelY = arrayOf(
//            intArrayOf(-1, -2, -1),
//            intArrayOf(0, 0, 0),
//            intArrayOf(1, 2, 1)
//        )
//
//        for (y in 1 until height - 1) {
//            for (x in 1 until width - 1) {
//                var gx = 0
//                var gy = 0
//
//                for (i in 0..2) {
//                    for (j in 0..2) {
//                        val pixel = bitmap.getPixel(x + i - 1, y + j - 1)
//                        val intensity = Color.red(pixel)
//                        gx += intensity * sobelX[i][j]
//                        gy += intensity * sobelY[i][j]
//                    }
//                }
//
//                val magnitude = Math.sqrt((gx * gx + gy * gy).toDouble()).toInt()
//                val color = Color.rgb(magnitude, magnitude, magnitude)
//                sobelBitmap.setPixel(x, y, color)
//            }
//        }
//
//        return sobelBitmap
//    }
//
//
//    private fun applyThreshold(bitmap: Bitmap, threshold: Int = 128): Bitmap {
//        val width = bitmap.width
//        val height = bitmap.height
//        val thresholdBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//
//        for (y in 0 until height) {
//            for (x in 0 until width) {
//                val pixel = bitmap.getPixel(x, y)
//                val intensity = Color.red(pixel)
//
//                val color = if (intensity > threshold) Color.WHITE else Color.BLACK
//                thresholdBitmap.setPixel(x, y, color)
//            }
//        }
//
//        return thresholdBitmap
//    }
//
//
//    private fun calculateAverageGrayValue(bitmap: Bitmap): Double {
//        val width = bitmap.width
//        val height = bitmap.height
//
//        var blackPixelCount = 0
//        val totalPixelCount = width * height
//
//        for (y in 0 until height) {
//            for (x in 0 until width) {
//                val pixel = bitmap.getPixel(x, y)
//                val intensity = Color.red(pixel)
//
//                if (intensity == 0) { // Ignore pure black and white pixels
//                    blackPixelCount++
//                }
//            }
//        }
//
//        // Calculate the percentage of black pixels in the image
//        return (blackPixelCount.toDouble() / totalPixelCount) * 100
//    }
//
//    fun determineClarityLevel(bitmap: Bitmap): Double {
//        val grayscaleBitmap = convertToGrayscale(bitmap)
//        val sobelBitmap = applySobelFilter(grayscaleBitmap)
//        val thresholdBitmap = applyThreshold(sobelBitmap)
//        return calculateAverageGrayValue(thresholdBitmap)
//    }
//}