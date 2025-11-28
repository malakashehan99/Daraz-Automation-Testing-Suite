# Daraz Automation Testing Project

This project is a **Maven** + **Selenium** + **TestNG** automation suite designed to test key functionalities of the **Daraz.lk** e-commerce website.
It covers search, filters, sorting, pagination, product details, image gallery, and an end-to-end user flow.

## Features
- Launch website test
- Search functionality — valid & invalid inputs
- Apply price filter and verify behavior
- Sort products (Low to High)
- Open first product from search results
- Verify product details (title, price, images)
- Image gallery thumbnail testing
- Pagination verification
- Complete end-to-end shopping flow test
- Reusable BaseTest with Selenium Manager

## Technologies Used
Selenium WebDriver 4.35.0  
TestNG 7.10.2  
Maven  
Selenium Manager  

## How to Run the Tests
mvn clean install  
mvn test -DsuiteXmlFile=testng.xml  
