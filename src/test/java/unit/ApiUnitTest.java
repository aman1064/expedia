package unit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import test.expedia.GetWeatherInfo;

@RunWith(MockitoJUnitRunner.class)
public class ApiUnitTest {
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    PrintWriter mockOutput;        

@Before
public void initMocks()throws ServletException, IOException {
    mockRequest = mock(HttpServletRequest.class);
    mockResponse = mock(HttpServletResponse.class);
    mockOutput=new PrintWriter("response.txt");
}


@Test
public void weatherTest() throws ServletException, IOException {

        System.out.println("In Weather test");
        when(mockRequest.getParameter("zip")).thenReturn("12345");
        when(mockResponse.getWriter()).thenReturn(mockOutput);
        new GetWeatherInfo().doGet(mockRequest, mockResponse);
        mockOutput.flush();
        String resString=FileUtils.readFileToString(new File("response.txt"));        
        TestCase.assertEquals(resString.contains("\"Temperature\""), true);
        TestCase.assertEquals(resString.contains("\"City\""), true);
        TestCase.assertEquals(resString.contains("\"State Name\""), true);
}

@Test
public void errorTestEnexistantCity() throws ServletException, IOException {

        System.out.println("In Non Existant city test");
        when(mockRequest.getParameter("zip")).thenReturn("94007");
        when(mockResponse.getWriter()).thenReturn(mockOutput);
        new GetWeatherInfo().doGet(mockRequest, mockResponse);
        mockOutput.flush();
        String resString=FileUtils.readFileToString(new File("response.txt"));        
        TestCase.assertEquals(resString.contains("\"error\""), true);
        TestCase.assertEquals(resString.contains("\"zipcode not found\""), true);
}
@Test
public void errorTestInValidCode() throws ServletException, IOException {

        System.out.println("In Invalid Code Error test");
        when(mockRequest.getParameter("zip")).thenReturn("9407");
        when(mockResponse.getWriter()).thenReturn(mockOutput);
        new GetWeatherInfo().doGet(mockRequest, mockResponse);
        mockOutput.flush();
        String resString=FileUtils.readFileToString(new File("response.txt"));        
        TestCase.assertEquals(resString.contains("\"error\""), true);
        TestCase.assertEquals(resString.contains("\"invalid zip code format\""), true);
}
}