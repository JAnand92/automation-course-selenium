﻿using Automation.Core.Components;
using Automation.Core.Testing;
using Automation.Framework.Ui.Pages;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Automation.Testing.Cases
{
    public class CreateStudent : TestCase
    {
        public override bool AutomationTest(IDictionary<string, object> testParams)
        {
            // testing data
            var firstName = $"{testParams["firstName"]}";
            var lastName = $"{testParams["lastName"]}";

            // perform test case
            return new FluentUi(Driver)
                .ChangeContext<StudentsUi>($"{testParams["application"]}")
                .Create()
                .FirstName(firstName)
                .LastName(lastName)
                .EnrollementDate(DateTime.Now)
                .Create()
                .FindByName(firstName)
                .Students()
                .Any();
        }
    }
}