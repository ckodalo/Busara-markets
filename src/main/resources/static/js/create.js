document.addEventListener('DOMContentLoaded', function() {
    var marketTypeElement = document.getElementById('marketType');
    if (marketTypeElement) {
        marketTypeElement.addEventListener('change', function() {
            try {
                var marketType = this.value;
                var yesNoSection = document.getElementById('yesNoSection');
                var multipleChoicePollSection = document.getElementById('multipleChoicePollSection');

                if (marketType === 'YesNo') {
                    yesNoSection.classList.remove('hidden');
                    multipleChoicePollSection.classList.add('hidden');

                    // Remove all security inputs
                    while (multipleChoicePollSection.firstChild) {
                        multipleChoicePollSection.removeChild(multipleChoicePollSection.firstChild);
                    }

                    // Append hidden inputs for 'yes' and 'no'
                    var inputYes = document.createElement('input');
                    inputYes.setAttribute('type', 'hidden');
                    inputYes.setAttribute('name', 'security');
                    inputYes.setAttribute('value', 'YES');

                    var inputNo = document.createElement('input');
                    inputNo.setAttribute('type', 'hidden');
                    inputNo.setAttribute('name', 'security');
                    inputNo.setAttribute('value', 'NO');

                    yesNoSection.appendChild(inputYes);
                    yesNoSection.appendChild(inputNo);

                } else if (marketType === 'MultipleChoice' || marketType === 'Poll') {
                    yesNoSection.classList.add('hidden');
                    multipleChoicePollSection.classList.remove('hidden');
                    addSecurityInput();
                }
            } catch (error) {
                console.error('An error occurred:', error);
            }
        });

        function addSecurityInput() {
            try {
                var securitiesDiv = document.getElementById('multipleChoicePollSection');
                var securityCount = securitiesDiv.getElementsByClassName('security-input').length + 1;

                var securityInput = document.createElement('div');
                securityInput.classList.add('security-input', 'flex', 'items-center', 'space-x-2');

                var label = document.createElement('label');
                label.setAttribute('for', 'security' + securityCount);
                label.classList.add('block', 'font-medium', 'text-sm', 'text-gray-700');
                label.innerText = 'Security:';

                var input = document.createElement('input');
                input.setAttribute('type', 'text');
                input.classList.add('border-gray-300', 'focus:border-indigo-500', 'focus:ring-indigo-500', 'rounded-md', 'shadow-sm', 'flex-1');
                input.setAttribute('id', 'security' + securityCount);
                input.setAttribute('name', 'security');
                input.setAttribute('required', '');

                var removeButton = document.createElement('button');
                removeButton.setAttribute('type', 'button');
                removeButton.classList.add('px-2', 'py-1', 'bg-red-500', 'text-white', 'rounded-md', 'hover:bg-red-600', 'focus:outline-none');
                removeButton.innerText = 'Remove';
                removeButton.addEventListener('click', function() {
                    securitiesDiv.removeChild(securityInput);
                });

                securityInput.appendChild(label);
                securityInput.appendChild(input);
                securityInput.appendChild(removeButton);
                securitiesDiv.appendChild(securityInput);
            } catch (error) {
                console.error('An error occurred in addSecurityInput:', error);
            }
        }
    } else {
        console.error('Element with id "marketType" not found.');
    }
});
