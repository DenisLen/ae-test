XML Crawler
This application trying to find XPath to element by searching identical id.
 If id not found it searching by identical attributes from origin element.
It return XPath of element which has more identical attributes with origin element

To build project:

gradle makeJar

How to use jar file:

-simple way to use exist samples:
java -jar <path_to_jar>

- to use with your samples:
java -jar <path_to_jar> <path_to_origin_sample> <path_to_sample_with_difference>

-to use with your samples and element id:
java -jar <path_to_jar> <path_to_origin_sample> <path_to_sample_with_difference> <element_id>