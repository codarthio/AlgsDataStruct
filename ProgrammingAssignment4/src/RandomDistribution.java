import java.util.Random;
import java.util.Scanner;

public class RandomDistribution {

    public static boolean linearDistribution(double p){

        return Math.random() * 100 < p;
    }
    public static boolean gaussianDistribution(double p, double d){

        Random r = new Random();
        double t = r.nextGaussian() * d + p;
        return t > p - d && t < p + d;


    }


    public static void main(String args[]) {
        int numTimes = 100000;
        double linTimes = 0;
        double gaussTimes = 0;
        double linMean = 0.0;
        double gaussMean = 0.0;

        for (int i = 0; i < numTimes; i++) {

            double c = 0;
            double total = 0;
            double percentTimeRight = 0.0;
            double percentage = 70.0;
            long s = System.nanoTime();

/*
            while (percentTimeRight <= percentage - .1 || percentTimeRight >= percentage + .1) {
                total++;

                if (linearDistribution(percentage)) c++;

                percentTimeRight = 100 * c / total;
                if(System.nanoTime() - s > 5000000) break;


            }
            linTimes +=total;

*/
            c = 0.0;
            total = 0.0;
            percentTimeRight = 0.0;


            double standardDev = 2;

            while (percentTimeRight <= percentage - .1|| percentTimeRight >= percentage + .1) {
                total++;

                if (gaussianDistribution(percentage, standardDev)) c++;

                percentTimeRight = 100 * c / total;
                if(System.nanoTime() - s > 5000000) break;


            }
            gaussTimes += total;
        }

        linMean = linTimes / numTimes;
        gaussMean = gaussTimes / numTimes;

        System.out.println("Running this program " + numTimes + " times, you would need to, on average," +
                "run it " + linMean + " times for Linear Distribution and \n" +
                "" + gaussMean + " times for Gaussian Distribution.");

    }
}
