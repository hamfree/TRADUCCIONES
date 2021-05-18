<!DOCTYPE html>
<html>
  <head><title>MVC Example: Finding State Abbreviations</title>
    <link rel="stylesheet"
          href="./css/styles.css"
          type="text/css"/>
  </head>
  <body>
    <div align="center">
      <table class="title">
        <tr><th>MVC Example: Finding State Abbreviations</th></tr>
      </table>
      <fieldset>
        <legend>Mapping US State Names to Post Office Abbreviations</legend>
        <form action="show-abbreviation-2">
          Full state name: 
          <input type="text" NAME="state-name" value="${stateInfo2.stateName}"><br/>
          <!-- Above is safe because the expression language outputs empty strings,
               not null pointer exceptions, for missing beans. -->
          <input type="submit" value="Show Abbreviation"/>
        </form>
      </fieldset>
    </div></body></html>