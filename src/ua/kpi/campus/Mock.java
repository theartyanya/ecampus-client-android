package ua.kpi.campus;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/16/13
 */
public class Mock {
    public static String getAUTH() {
        return AUTH;
    }

    public static String getUSER_EMPLOYEE() {
        return USER_EMPLOYEE;
    }

    public static String getUSER_CONVERSATION() {
        return USER_CONVERSATION;
    }

    private static final String BULLETIN_BOARD_ACTUAL = "{\n" +
            "  \"StatusCode\": 200,\n" +
            "  \"TimeStamp\": \"2013-12-25T12:16:22.4395979-08:00\",\n" +
            "  \"Guid\": \"08f67e25-f4cb-4566-bf17-de41e6fe7854\",\n" +
            "  \"Paging\": null,\n" +
            "  \"Data\": [\n" +
            "    {\n" +
            "      \"Text\": \"Увага всім викладачам кафедри Технічної кібернетики!\",\n" +
            "      \"DateCreate\": \"2013-12-12T00:00:00\",\n" +
            "      \"CreatorUserAccountId\": 9,\n" +
            "      \"CreatorUserFullName\": \"Мелкумян Катерина Юріївна\",\n" +
            "      \"BulletinBoardSubjectId\": 0,\n" +
            "      \"BulletinBoardId\": 25,\n" +
            "      \"BulletinBoardLinkRecipients\": null,\n" +
            "      \"Subject\": \"Об'ява для всіх викладачів кафедри Технічної кібернетики\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Text\": \"Увага Вкладачам НТУУ \\\"КПІ\\\"!\",\n" +
            "      \"DateCreate\": \"2013-12-12T00:00:00\",\n" +
            "      \"CreatorUserAccountId\": 11,\n" +
            "      \"CreatorUserFullName\": null,\n" +
            "      \"BulletinBoardSubjectId\": 0,\n" +
            "      \"BulletinBoardId\": 32,\n" +
            "      \"BulletinBoardLinkRecipients\": null,\n" +
            "      \"Subject\": \"Об'ява для всіх викладачів НТУУ \\\"КПІ\\\"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static String getBulletinBoardActual() {
        return BULLETIN_BOARD_ACTUAL;
    }

    private static final String AUTH = "{\n" +
            "  \"StatusCode\": 200,\n" +
            "  \"TimeStamp\": \"2013-12-08T19:44:36.428033-08:00\",\n" +
            "  \"Guid\": \"e9795ef3-f417-4a93-8aec-19e766fb7f73\",\n" +
            "  \"Paging\": null,\n" +
            "  \"Data\": \"178945ba-3cca-4968-8e1c-2a13ea488fb1\"\n" +
            "}";

    private static final String USER_EMPLOYEE = "{\n" +
            "  \"StatusCode\": 200,\n" +
            "  \"TimeStamp\": \"2013-12-08T19:45:49.0773606-08:00\",\n" +
            "  \"Guid\": \"84e02c79-b305-4e1d-abfe-48d41797d158\",\n" +
            "  \"Paging\": null,\n" +
            "  \"Data\": {\n" +
            "    \"UserAccountId\": 11,\n" +
            "    \"Photo\": \"http://hasslefreeliving.com/wp-content/uploads/2012/10/placeholder.gif\",\n" +
            "    \"FullName\": \"Савицький Артем Йосипович\",\n" +
            "    \"ScientificInterest\": null,\n" +
            "    \"Personalities\": [],\n" +
            "    \"Employees\": [\n" +
            "      {\n" +
            "        \"SubdivisionId\": 10193,\n" +
            "        \"SubdivisionName\": \"Кафедра технiчної кiбернетики ФІОТ\",\n" +
            "        \"Position\": \"Штатний Доцент    \",\n" +
            "        \"AcademicDegree\": \"Кандидат наук\",\n" +
            "        \"AcademicStatus\": \"Доцент\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Profiles\": [\n" +
            "      {\n" +
            "        \"SubsystemName\": \"Повідомлення (Викладач)\",\n" +
            "        \"IsCreate\": true,\n" +
            "        \"IsRead\": true,\n" +
            "        \"IsUpdate\": true,\n" +
            "        \"IsDelete\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"SubsystemName\": \"Дошка оголошень (Викладач)\",\n" +
            "        \"IsCreate\": false,\n" +
            "        \"IsRead\": true,\n" +
            "        \"IsUpdate\": false,\n" +
            "        \"IsDelete\": false\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    private static final String USER_CONVERSATION =
            "{\n" +
                    "  \"StatusCode\": 200,\n" +
                    "  \"TimeStamp\": \"2013-12-13T15:16:51.3175353-08:00\",\n" +
                    "  \"Guid\": \"a70b4867-a7df-4f58-b422-4cc75e46f3ea\",\n" +
                    "  \"Paging\": null,\n" +
                    "  \"Data\": [\n" +
                    "    {\n" +
                    "      \"Subject\": \"Мелкумян Катерина Юріївна Савицький Артем Йосипович Свинаренко Дмитро Анатолійович \",\n" +
                    "      \"Users\": [\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 11,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=11\",\n" +
                    "          \"FullName\": \"Савицький Артем Йосипович\",\n" +
                    "          \"ScientificInterest\": null,\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 9,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=9\",\n" +
                    "          \"FullName\": \"Мелкумян Катерина Юріївна\",\n" +
                    "          \"ScientificInterest\": \"интеллектуальные информационные системы\",\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 12,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=12\",\n" +
                    "          \"FullName\": \"Свинаренко Дмитро Анатолійович\",\n" +
                    "          \"ScientificInterest\": null,\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"LastMessageText\": \"hello_to_all2\",\n" +
                    "      \"LastMessageDate\": \"2013-12-08T12:47:15.667\",\n" +
                    "      \"GroupId\": 2\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Subject\": \"Мелкумян Катерина Юріївна Савицький Артем Йосипович \",\n" +
                    "      \"Users\": [\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 11,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=11\",\n" +
                    "          \"FullName\": \"Савицький Артем Йосипович\",\n" +
                    "          \"ScientificInterest\": null,\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 9,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=9\",\n" +
                    "          \"FullName\": \"Мелкумян Катерина Юріївна\",\n" +
                    "          \"ScientificInterest\": \"интеллектуальные информационные системы\",\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"LastMessageText\": \"piska\",\n" +
                    "      \"LastMessageDate\": \"2013-12-09T13:25:51.607\",\n" +
                    "      \"GroupId\": 3\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Subject\": \"super_Group\",\n" +
                    "      \"Users\": [\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 11,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=11\",\n" +
                    "          \"FullName\": \"Савицький Артем Йосипович\",\n" +
                    "          \"ScientificInterest\": null,\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 18,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=18\",\n" +
                    "          \"FullName\": \"Тимошин Юрій Афанасійович\",\n" +
                    "          \"ScientificInterest\": null,\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 19,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=19\",\n" +
                    "          \"FullName\": \"Шемсединов Тимур Гафарович\",\n" +
                    "          \"ScientificInterest\": null,\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"UserAccountId\": 20,\n" +
                    "          \"Photo\": \"http://api.ecampus.kpi.ua/Storage/GetUserProfileImage?userId=20\",\n" +
                    "          \"FullName\": \"Габзовська Ольга  Борисівна\",\n" +
                    "          \"ScientificInterest\": null,\n" +
                    "          \"Personalities\": null,\n" +
                    "          \"Employees\": null,\n" +
                    "          \"Profiles\": null\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"LastMessageText\": \"hello_my_dear_friend\",\n" +
                    "      \"LastMessageDate\": \"2013-12-10T11:05:08.193\",\n" +
                    "      \"GroupId\": 5\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

    public static String getMessageItem() {
        return MESSAGE_ITEM;
    }

    private static final String MESSAGE_ITEM = "{\n" +
            "  \"StatusCode\": 200,\n" +
            "  \"TimeStamp\": \"2013-12-19T16:57:05.4428835-08:00\",\n" +
            "  \"Guid\": \"50e8104b-51ab-47ec-97ff-d53c7d04e2b7\",\n" +
            "  \"Paging\": {\n" +
            "    \"PageCount\": 1,\n" +
            "    \"TotalItemCount\": 4,\n" +
            "    \"PageNumber\": 1,\n" +
            "    \"PageSize\": 20,\n" +
            "    \"HasPreviousPage\": false,\n" +
            "    \"HasNextPage\": false,\n" +
            "    \"IsFirstPage\": true,\n" +
            "    \"IsLastPage\": true,\n" +
            "    \"FirstItemOnPage\": 1,\n" +
            "    \"LastItemOnPage\": 4\n" +
            "  },\n" +
            "  \"Data\": [\n" +
            "    {\n" +
            "      \"SenderUserAccountId\": 14,\n" +
            "      \"MessageId\": 96,\n" +
            "      \"MassageGroupId\": 3,\n" +
            "      \"MessageDetail\": {\n" +
            "        \"MessageDetailsId\": 84,\n" +
            "        \"DateView\": \"12/18/2013 1:10:45 PM\"\n" +
            "      },\n" +
            "      \"DateSent\": \"12/18/2013 1:10:45 PM\",\n" +
            "      \"Subject\": \"-\",\n" +
            "      \"Text\": \"лплпд\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"SenderUserAccountId\": 11,\n" +
            "      \"MessageId\": 95,\n" +
            "      \"MassageGroupId\": 3,\n" +
            "      \"MessageDetail\": {\n" +
            "        \"MessageDetailsId\": 83,\n" +
            "        \"DateView\": \"12/18/2013 1:10:25 PM\"\n" +
            "      },\n" +
            "      \"DateSent\": \"12/18/2013 1:10:25 PM\",\n" +
            "      \"Subject\": \"-\",\n" +
            "      \"Text\": \"добрый день!\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"SenderUserAccountId\": 11,\n" +
            "      \"MessageId\": 4,\n" +
            "      \"MassageGroupId\": 3,\n" +
            "      \"MessageDetail\": null,\n" +
            "      \"DateSent\": \"12/9/2013 1:25:51 PM\",\n" +
            "      \"Subject\": \"-\",\n" +
            "      \"Text\": \"hello\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"SenderUserAccountId\": 12,\n" +
            "      \"MessageId\": 3,\n" +
            "      \"MassageGroupId\": 3,\n" +
            "      \"MessageDetail\": {\n" +
            "        \"MessageDetailsId\": 7,\n" +
            "        \"DateView\": \"12/10/2013 12:29:08 AM\"\n" +
            "      },\n" +
            "      \"DateSent\": \"12/9/2013 11:32:32 AM\",\n" +
            "      \"Subject\": \"-\",\n" +
            "      \"Text\": \"hello\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
