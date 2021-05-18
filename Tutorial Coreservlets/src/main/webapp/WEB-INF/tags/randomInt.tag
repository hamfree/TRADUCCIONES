<%@ attribute name="limit" required="false" %>
<% 
int lim = 10;
try {
  lim = Integer.parseInt(limit);
} catch(NumberFormatException nfe) {}
%>
<%= 1 + (int)(Math.random() * lim) %>
