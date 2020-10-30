package org.bouncycastle;

public class LICENSE {
    public static String licenseText;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("Copyright (c) 2000-2014 The Legion of the Bouncy Castle Inc. (http://www.bouncycastle.org) ");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("Permission is hereby granted, free of charge, to any person obtaining a copy of this software ");
        sb.append(System.getProperty("line.separator"));
        sb.append("and associated documentation files (the \"Software\"), to deal in the Software without restriction, ");
        sb.append(System.getProperty("line.separator"));
        sb.append("including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, ");
        sb.append(System.getProperty("line.separator"));
        sb.append("and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,");
        sb.append(System.getProperty("line.separator"));
        sb.append("subject to the following conditions:");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("The above copyright notice and this permission notice shall be included in all copies or substantial");
        sb.append(System.getProperty("line.separator"));
        sb.append("portions of the Software.");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,");
        sb.append(System.getProperty("line.separator"));
        sb.append("INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR");
        sb.append(System.getProperty("line.separator"));
        sb.append("PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE");
        sb.append(System.getProperty("line.separator"));
        sb.append("LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR");
        sb.append(System.getProperty("line.separator"));
        sb.append("OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER");
        sb.append(System.getProperty("line.separator"));
        sb.append("DEALINGS IN THE SOFTWARE.");
        licenseText = sb.toString();
    }

    public static void main(String[] strArr) {
        System.out.println(licenseText);
    }
}
