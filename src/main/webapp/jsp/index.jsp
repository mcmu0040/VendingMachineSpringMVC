<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" crossorigin="anonymous">

        <title>McMullen Vending Machine</title>

        <style>
            div.itemBox{
                border: 1px;
                border-radius: 15px;
                margin: 0px;
            }
            div.itemBox:hover {
                background-color: rgb(15, 139, 102);
                opacity: 0.75;
                font-weight: bold;
            }
            input {
                text-align: center;
                width: 100%;              
            }
            button {
                width: 125px;
                align-self: center;
            }
        </style>
    </head>
    <body>
        <h1>Vending Machine</h1>

        <table class="table">
            <tr>
                <td style="width: 70%">
                    <div class="row" id="vendingDisplay">
                        <c:forEach var="item" items="${items}">
                            <form method="post" action="${pageContext.request.contextPath}/selectItem/${item.id}">
                                <button type="submit">
                                    <div style="" class="itemBox">
                                        <p style="text-align: left">${item.id}</p>
                                        ${item.name}<br>
                                        <p>${item.price}</p>
                                        <p>QTY: ${item.quantity}</p>
                                    </div>
                                </button>
                            </form>
                        </c:forEach>
                    </div>
                </td>
                <td style="width: 30%" class="menu">
                    <div id="total">
                        <h3 id="total">Total $ In</h3>
                        <input readonly type="text" id="totalDisplay" value="${cash}">
                        <br>
                        <div class="row">
                            <form method="post" action="${pageContext.request.contextPath}/addMoney/100">
                                <button type="submit" class="btn btn-default">Add dollar</button>
                            </form>
                            <form method="post" action="${pageContext.request.contextPath}/addMoney/25">
                                <button type="submit" class="btn btn-default">Add quarter</button>
                            </form>
                        </div>
                        <div class="row">
                            <form method="post" action="${pageContext.request.contextPath}/addMoney/10">
                                <button type="submit" class="btn btn-default">Add dime</button>
                            </form>
                            <form method="post" action="${pageContext.request.contextPath}/addMoney/5">
                                <button type="submit" class="btn btn-default">Add nickel</button>
                            </form>
                        </div>
                    </div>

                    <hr>

                    <div id="messages">

                        <h3>Messages</h3>
                        <input readonly type="text" id="messageDisplay" value="${message}">
                        <br>
                        Item: <input readonly type="text" value="${selected}" style="width: 30px">
                        <br>
                        <form method="post" action="${pageContext.request.contextPath}/makePurchase/${selected}">    
                            <button type="submit" class="btn btn-default">Make Purchase</button>
                        </form>
                    </div>
                    <hr>
                    <div id="change">
                        <h3>Change</h3>
                        <!--<form>-->
                        <input readonly type="text" id="changeDisplay" value="${change}">
                        <br>
                        <form method="post" action="${pageContext.request.contextPath}/resetAll">
                            <button type="submit" class="btn btn-default">Change Return</button>
                        </form>
                        <!--</form>-->
                    </div>

                </td>

            </tr>

        </table>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

